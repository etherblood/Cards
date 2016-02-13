package com.etherblood.cardsmasterserver.matches;

import com.etherblood.cardsmasterserver.matches.internal.IdConverter;
import com.etherblood.cardsmasterserver.matches.internal.MatchWrapper;
import com.etherblood.cardsmasterserver.matches.internal.EventToMessageConverter;
import com.etherblood.cardsmasterserver.matches.internal.ClientUpdaterFactory;
import com.etherblood.cardsmasterserver.matches.internal.players.HumanPlayer;
import com.etherblood.cardsmasterserver.cards.CardCollectionService;
import com.etherblood.cardsmasterserver.cards.CardTemplatesService;
import com.etherblood.cardsmasterserver.matches.internal.MatchLogger;
import com.etherblood.cardsmasterserver.matches.internal.MatchResult;
import com.etherblood.cardsmasterserver.matches.internal.players.AbstractPlayer;
import com.etherblood.cardsmasterserver.matches.internal.players.AiPlayer;
import com.etherblood.cardsmasterserver.network.connections.UserConnectionService;
import com.etherblood.cardsmasterserver.network.messages.MessageHandler;
import com.etherblood.cardsmasterserver.system.SystemTaskEvent;
import com.etherblood.cardsmatch.cardgame.DefaultMatch;
import com.etherblood.cardsmatch.cardgame.MatchState;
import com.etherblood.cardsmatch.cardgame.bot.AlphaBetaBot;
import com.etherblood.cardsmatch.cardgame.bot.Bot;
import com.etherblood.cardsmatch.cardgame.bot.Evaluation;
import com.etherblood.cardsmatch.cardgame.bot.monteCarlo.MonteCarloBot;
import com.etherblood.cardsmatch.cardgame.events.effects.TargetedTriggerEffectEvent;
import com.etherblood.cardsnetworkshared.master.commands.MatchRequest;
import com.etherblood.cardsnetworkshared.match.commands.TriggerEffectRequest;
import com.etherblood.cardsnetworkshared.match.misc.CardsMessage;
import com.etherblood.cardsnetworkshared.match.misc.MatchUpdate;
import com.etherblood.entitysystem.data.EntityId;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.concurrent.ConcurrentHashMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.event.EventListener;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

/**
 *
 * @author Philipp
 */
@Service
public class MatchService {
    private final ConcurrentHashMap<Long, HumanPlayer> matchMap = new ConcurrentHashMap<>();
    private Long pendingUserId = null;
    
    @Autowired
    private UserConnectionService connectionService;
    @Autowired
    private CardTemplatesService templateService;
    @Autowired
    private CardCollectionService collectionService;
    @Autowired
    private ApplicationEventPublisher eventPublisher;
    
    @MessageHandler
    @PreAuthorize("hasRole('ROLE_USER')")
    public void onTriggerEffectRequest(TriggerEffectRequest triggerEffect) {
        HumanPlayer matchPlayer = matchMap.get(connectionService.getCurrentUserId());
        matchPlayer.triggerEffect(triggerEffect.getSource(), triggerEffect.getTargets());
        MatchWrapper match = matchPlayer.getMatch();
        updateMatch(match);
    }
    
    @MessageHandler
    @PreAuthorize("hasRole('ROLE_USER')")
    public synchronized void onMatchRequest(MatchRequest matchRequest) {
        if(matchRequest.isVersusBot()) {
            startMatch(connectionService.getCurrentUserId(), null);
        } else if(pendingUserId != null) {
            startMatch(connectionService.getCurrentUserId(), pendingUserId);
            pendingUserId = null;
        } else {
            pendingUserId = connectionService.getCurrentUserId();
        }
    }
    
    private void startMatch(long user1, Long user2) {
        ArrayList<AbstractPlayer> players = new ArrayList<>();
        ArrayList<HumanPlayer> humans = new ArrayList<>();
        EventToMessageConverter eventToMessageConverter = new EventToMessageConverter(Collections.unmodifiableList(humans), ClientUpdaterFactory.createUpdateBuilders());
        final DefaultMatch match = new DefaultMatch(templateService.getAll(), eventToMessageConverter);
        eventToMessageConverter.matchLogger = new MatchLogger(match.data);
        MatchWrapper matchWrapper = new MatchWrapper(match, players);
        EntityId player1 = match.idFactory.createEntity();
        EntityId player2 = match.idFactory.createEntity();
        
        String[] library1, library2;
        HumanPlayer matchPlayer1 = new HumanPlayer(user1, matchWrapper, player1);
        players.add(matchPlayer1);
        humans.add(matchPlayer1);
        IdConverter converter = matchPlayer1.getConverter();
        converter.register(player1);
        converter.register(player2);
        List<String> cards = collectionService.getActiveUserLibrary(user1).getCards();
        library1 = cards.toArray(new String[cards.size()]);
        
        AbstractPlayer matchPlayer2;
        if(user2 != null) {
            matchPlayer2 = new HumanPlayer(user2.longValue(), matchWrapper, player2);
            IdConverter converter2 = ((HumanPlayer)matchPlayer2).getConverter();
            converter2.register(player2);
            converter2.register(player1);
            humans.add((HumanPlayer) matchPlayer2);
            
            cards = collectionService.getActiveUserLibrary(user2.longValue()).getCards();
            library2 = cards.toArray(new String[cards.size()]);
        } else {
//            Bot bot = new AlphaBetaBot(new Evaluation(), match);
            MonteCarloBot bot = new MonteCarloBot(match, player1);
            bot.setVerbose(true);
            bot.setTimeoutMillis(5000);
            matchPlayer2 = new AiPlayer(matchWrapper, player2, bot);
            library2 = createBotLibrary();
        }
        players.add(matchPlayer2);
        
        match.startGame(player1, library1, player2, library2);
        
        for (HumanPlayer human : humans) {
            matchMap.put(human.getUserId(), human);
        }
        updateMatch(matchWrapper);
    }
    
    @EventListener
    @PreAuthorize("hasRole('ROLE_SYSTEM')")
    public void aiUpdate(AiUpdateRequest request) {
        MatchWrapper matchWrapper = request.getMatchWrapper();
        AbstractPlayer currentPlayer = matchWrapper.getCurrentPlayer();
        if(currentPlayer instanceof AiPlayer && !matchWrapper.hasMatchEnded()) {
            try {
                ((AiPlayer)currentPlayer).compute();
            } finally {
                updateMatch(matchWrapper);
            }
        }
    }

    private void updateMatch(MatchWrapper match) {
        broadcastChanges(match);
        if(match.hasMatchEnded()) {
            cleanupMatch(match);
        } else {
            eventPublisher.publishEvent(new SystemTaskEvent(new AiUpdateRequest(match)));
        }
    }
    
    private void cleanupMatch(MatchWrapper matchWrapper) {
        MatchResult result = matchWrapper.getResult();
        for (AbstractPlayer player : result.getWinners()) {
            if(player instanceof HumanPlayer) {
                eventPublisher.publishEvent(new SystemTaskEvent(new WonMatchEvent((HumanPlayer) player)));
            }
        }
        for (HumanPlayer matchPlayer : matchWrapper.getPlayers(HumanPlayer.class)) {
            matchMap.remove(matchPlayer.getUserId());
        }
        System.out.println("Match ended");
    }

    private void broadcastChanges(MatchWrapper matchWrapper) {
        for (HumanPlayer matchPlayer : matchWrapper.getPlayers(HumanPlayer.class)) {
            connectionService.sendMessages(matchPlayer.getUserId(), messagesFromUpdates(matchPlayer.getLatestUpdates()));
        }
    }
    
    private CardsMessage[] messagesFromUpdates(List<MatchUpdate> updates) {
        CardsMessage[] messages = new CardsMessage[updates.size()];
        for (int i = 0; i < updates.size(); i++) {
            messages[i] = new CardsMessage(updates.get(i));
        }
        return messages;
    }
    
    private String[] createBotLibrary() {
        String[] COLLECTIBLE_TEMPLATES = templateService.getCollectibles();
        Random rng = new Random();
        String[] library = new String[30];
        for (int i = 0; i < library.length; i++) {
            library[i] = COLLECTIBLE_TEMPLATES[rng.nextInt(COLLECTIBLE_TEMPLATES.length)];
        }
        return library;
    }
}
