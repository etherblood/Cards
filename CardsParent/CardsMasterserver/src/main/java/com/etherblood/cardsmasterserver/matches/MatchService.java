package com.etherblood.cardsmasterserver.matches;

import com.etherblood.cardsmasterserver.cards.CardService;
import com.etherblood.cardsmasterserver.cards.model.Card;
import com.etherblood.cardsmasterserver.cards.model.CardGroup;
import com.etherblood.logging.DefaultLogger;
import com.etherblood.logging.LogLevel;
import com.etherblood.cardsmasterserver.matches.internal.players.HumanPlayer;
import com.etherblood.cardsmasterserver.logging.LoggerService;
import com.etherblood.cardsmasterserver.matches.internal.MatchContextWrapper;
import com.etherblood.cardsmasterserver.matches.internal.players.AbstractPlayer;
import com.etherblood.cardsmasterserver.matches.internal.players.AiPlayer;
import com.etherblood.cardsmasterserver.network.connections.UserConnectionService;
import com.etherblood.cardsmasterserver.network.events.UserLogoutEvent;
import com.etherblood.cardsmasterserver.network.messages.MessageHandler;
import com.etherblood.cardsmasterserver.system.SystemTaskEvent;
import com.etherblood.cardsmasterserver.users.UserService;
import com.etherblood.cardsmasterserver.users.model.UserAccount;
import com.etherblood.cardsmatchapi.IllegalCommandException;
import com.etherblood.cardsnetworkshared.master.commands.MatchRequest;
import com.etherblood.cardsnetworkshared.match.commands.TriggerEffectRequest;
import com.etherblood.cardsnetworkshared.DefaultMessage;
import com.etherblood.cardsnetworkshared.match.misc.MatchUpdate;
import com.etherblood.cardsmatchapi.PlayerDefinition;
import com.etherblood.cardsmatchapi.RulesDefinition;
import com.etherblood.cardsmatchapi.MatchBuilder;
import com.etherblood.cardsmatchapi.PlayerResult;
import com.etherblood.cardsnetworkshared.master.commands.AvailableBotsRequest;
import com.etherblood.cardsnetworkshared.master.updates.AvailableBotsUpdate;
import com.etherblood.cardsnetworkshared.master.updates.BotTo;
import com.etherblood.cardsnetworkshared.match.updates.JoinedMatchUpdate;
import com.etherblood.logging.FormattedLogsWriterImpl;
import java.io.File;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Random;
import java.util.Set;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicReference;
import javax.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.event.EventListener;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author Philipp
 */
@Service
public class MatchService {

    private final ConcurrentHashMap<Long, HumanPlayer> matchMap = new ConcurrentHashMap<>();
    private final AtomicReference<MatchQueuePlayer> pendingPlayer = new AtomicReference<>(null);
    private final SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy.MM.dd_HH.mm.ss");
    private HashMap<String, RulesDefinition> rules;

    @Autowired
    private LoggerService loggerService;
    @Autowired
    private UserConnectionService connectionService;
    @Autowired
    private CardService cardsService;
    @Autowired
    private UserService userService;
    @Autowired
    private ApplicationEventPublisher eventPublisher;

    @Value("${matchLogs.path}")
    private String matchLogsPath;
    
    @PreAuthorize("denyAll")
    @Value("${rules.paths}")
    public void setRules(List<String> availableRulePaths) {
        rules = new HashMap<>();
        Arrays.toString(availableRulePaths.toArray());
        for (String availableRulePath : availableRulePaths) {
            try {
                Class rulesetClass = Class.forName(availableRulePath);
                RulesDefinition rule = (RulesDefinition) rulesetClass.newInstance();
                rules.put(rule.getName(), rule);
            } catch (ClassNotFoundException | InstantiationException | IllegalAccessException ex) {
                loggerService.getLogger(getClass()).log(LogLevel.ERROR, ex);
                ex.printStackTrace(System.out);
            }
        }
    }
    
    @Transactional
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public void registerCards() {
        for (RulesDefinition<?, ?> rule : rules.values()) {
            for (String templateName : rule.getTemplateNames()) {
                cardsService.tryRegisterCard(templateName);
            }
        }
    }

    @MessageHandler
    @PreAuthorize("hasRole('ROLE_USER')")
    public void onTriggerEffectRequest(TriggerEffectRequest triggerEffect) {
        HumanPlayer matchPlayer = matchMap.get(connectionService.getCurrentUserId());
        MatchContextWrapper match = matchPlayer.getMatch();
        synchronized (match) {
            try {
                matchPlayer.triggerEffect(triggerEffect);
                updateMatch(match);
            } catch (IllegalCommandException e) {
                match.getLogger().log(LogLevel.ERROR, e);
            } catch (Exception e) {
                cleanupMatchAfterException(e, match);
            }
        }
    }

    @MessageHandler
    @Transactional
    @PreAuthorize("hasRole('ROLE_USER')")
    public void onMatchRequest(MatchRequest matchRequest) {
        //TODO: make sure a user can only be once in the matchQueue/an active match
        //duplicate matchRequests should be discarded as should requests during a match
        UserAccount currentUser = connectionService.getCurrentUser();
        
        PlayerDefinition def = new PlayerDefinition();
        def.setHeroTemplate("Hero");
        def.setName(currentUser.getUsername());
        def.setLibrary(fetchLibrary(matchRequest.getLibraryId()));
        MatchQueuePlayer queuePlayer = new MatchQueuePlayer();
        queuePlayer.setUser(currentUser);
        queuePlayer.setDef(def);
        
        if (matchRequest.isVersusBot()) {
            BotTo bot = matchRequest.getBot();
            PlayerDefinition botDef = new PlayerDefinition();
            botDef.setLibrary(fetchLibrary(bot.library.id));
            botDef.setHeroTemplate("Hero");
            botDef.setName(bot.displayName);
            
            MatchQueuePlayer queueBot = new MatchQueuePlayer();
            queueBot.setDef(botDef);
            
            startRuleMatch(queuePlayer, queueBot);
        } else {
            while (!pendingPlayer.compareAndSet(null, queuePlayer)) {
                MatchQueuePlayer matchPartner = pendingPlayer.getAndSet(null);
                if (matchPartner != null) {
                    startRuleMatch(queuePlayer, matchPartner);
                    break;
                }
            }
        }
    }

    @EventListener
    @PreAuthorize("hasRole('ROLE_SYSTEM')")
    public void onUserLogout(UserLogoutEvent event) {
        MatchQueuePlayer queuePlayer = new MatchQueuePlayer();
        UserAccount userAccount = new UserAccount();
        userAccount.setId(event.getUserId());
        queuePlayer.setUser(userAccount);
        pendingPlayer.compareAndSet(queuePlayer, null);
    }

    private void startRuleMatch(MatchQueuePlayer queuePlayer1, MatchQueuePlayer queuePlayer2) {
        //TODO: reconnect instead of dropping previous matches
        killMatch(queuePlayer1.getUser().getId());
        if(!queuePlayer2.isBot()) {
            killMatch(queuePlayer2.getUser().getId());
        }
        
        RulesDefinition ruleset = rules.get("default rules");//new DefaultRulesDef(templateService.getAll());
        ArrayList<AbstractPlayer> players = new ArrayList<>();
        ArrayList<HumanPlayer> humans = new ArrayList<>();

        PlayerDefinition def1 = queuePlayer1.getDef();
        PlayerDefinition def2 = queuePlayer2.getDef();
        
        UUID uuid = UUID.randomUUID();
        String matchName = dateFormat.format(new Date()) + "_" + def1.getName() + "_vs_" + def2.getName() + "_" + uuid.toString();
        PrintWriter writer;
        try {
            writer = new PrintWriter(new File(matchLogsPath + matchName + ".txt"));
        } catch(Exception e) {
            throw new RuntimeException(e);
        }
        DefaultLogger logger = new DefaultLogger(new FormattedLogsWriterImpl(writer));
        logger.log(LogLevel.INFO, "Started match {} - {} vs {}", uuid, def1.getName(), def2.getName());
        MatchBuilder matchBuilder = ruleset.createMatchBuilder(logger);
        HumanPlayer player1 = new HumanPlayer(queuePlayer1.getUser().getId(), matchBuilder.createHuman(def1));
        humans.add(player1);
        players.add(player1);

        AbstractPlayer player2;
        if (!queuePlayer2.isBot()) {
            player2 = new HumanPlayer(queuePlayer2.getUser().getId(), matchBuilder.createHuman(def2));
            humans.add((HumanPlayer) player2);
        } else {
            AiPlayer aiPlayer = new AiPlayer(matchBuilder.createBot(def2));
            player2 = aiPlayer;
        }
        players.add(player2);

        final MatchContextWrapper wrapper = new MatchContextWrapper(uuid, logger);
        wrapper.init(matchBuilder.getStateTracker(), players);
        
        synchronized(wrapper) {
            for (HumanPlayer human : humans) {
                matchMap.put(human.getUserId(), human);
                connectionService.sendMessage(human.getUserId(), new DefaultMessage(new JoinedMatchUpdate()));
            }
            matchBuilder.start();
            assert wrapper.getCurrentPlayer() != null;
            updateMatch(wrapper);
        }
    }

    private void killMatch(long user1) {
        HumanPlayer oldPlayer = matchMap.remove(user1);
        if(oldPlayer != null) {
            MatchContextWrapper oldMatch = oldPlayer.getMatch();
            synchronized(oldMatch) {
                cleanupMatchAfterException(new IllegalStateException("user started new match when an old match was still in progress"), oldMatch);
            }
        }
    }

    @EventListener
    @PreAuthorize("hasRole('ROLE_SYSTEM')")
    public void aiUpdate(AiUpdateRequest request) {
        MatchContextWrapper matchWrapper = request.getMatchWrapper();
        synchronized (matchWrapper) {
            AbstractPlayer currentPlayer = matchWrapper.getCurrentPlayer();
            if (currentPlayer instanceof AiPlayer && !matchWrapper.hasMatchEnded()) {
                try {
                    ((AiPlayer) currentPlayer).compute();//TODO: can we avoid synchronized while the AI is thinking?
                    updateMatch(matchWrapper);
                } catch (Exception e) {
                    cleanupMatchAfterException(e, matchWrapper);
                }
            }
        }
    }

    private void cleanupMatchAfterException(Exception e, MatchContextWrapper matchWrapper) {
        e.printStackTrace(System.err);
        System.err.println("ending match because of exception");
        loggerService.getLogger(getClass()).log(LogLevel.WARN, "ending match because of exception");
        matchWrapper.getLogger().log(LogLevel.ERROR, e);
        loggerService.getLogger(getClass()).log(LogLevel.ERROR, e);
        //TODO: end match with exception result
        for (HumanPlayer player : matchWrapper.getPlayers(HumanPlayer.class)) {
            loggerService.getLogger(getClass()).log(LogLevel.ERROR, "MatchService - TODO: send matchAbort to user {}", player.getUserId());
            System.out.println("MatchService - TODO: send matchAbort to user " + player.getUserId());//TODO send matchAbort to user
        }
        cleanupMatch(matchWrapper);
    }

    private void updateMatch(MatchContextWrapper match) {
        broadcastChanges(match);
        if (match.hasMatchEnded()) {
            cleanupMatch(match);
        } else {
            eventPublisher.publishEvent(new SystemTaskEvent(new AiUpdateRequest(match)));
        }
    }

    private void cleanupMatch(MatchContextWrapper matchWrapper) {
        matchWrapper.getLogger().log(LogLevel.INFO, "Match ended");
        ((FormattedLogsWriterImpl)((DefaultLogger)matchWrapper.getLogger()).getFormatter()).getWriter().close();
        for (HumanPlayer matchPlayer : matchWrapper.getPlayers(HumanPlayer.class)) {
            matchMap.remove(matchPlayer.getUserId());
            if(matchWrapper.getResult(matchPlayer) == PlayerResult.VICTOR) {
                eventPublisher.publishEvent(new SystemTaskEvent(new WonMatchEvent(matchPlayer)));
            }
        }
        System.out.println("Match ended");
    }

    private void broadcastChanges(MatchContextWrapper matchWrapper) {
        for (HumanPlayer matchPlayer : matchWrapper.getPlayers(HumanPlayer.class)) {
            List<MatchUpdate> updates = matchPlayer.getLatestUpdates();
            if (updates.isEmpty()) {
                continue;
            }
            DefaultMessage[] messages = messagesFromUpdates(updates);
            loggerService.getLogger(getClass()).log(LogLevel.DEBUG, "{} messages for player with id {}", messages.length, matchPlayer.getUserId());
            System.out.println(messages.length + " messages for player with id " + matchPlayer.getUserId());
            connectionService.sendMessages(matchPlayer.getUserId(), messages);
        }
    }

    private DefaultMessage[] messagesFromUpdates(List<MatchUpdate> updates) {
        DefaultMessage[] messages = new DefaultMessage[updates.size()];
        for (int i = 0; i < updates.size(); i++) {
            messages[i] = new DefaultMessage(updates.get(i));
        }
        return messages;
    }

    private List<String> fetchLibrary(long libraryId) {
        Set<Card> libraryCards = cardsService.findCardGroup(libraryId).getCards();
        List<String> library = new ArrayList<>();
        for (Card libraryCard : libraryCards) {
            for (int j = 0; j < libraryCard.getAmount(); j++) {
                library.add(libraryCard.getTemplate().getName());
            }
        }
        return library;
    }
}
