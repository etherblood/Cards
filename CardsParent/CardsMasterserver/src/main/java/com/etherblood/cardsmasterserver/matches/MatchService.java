package com.etherblood.cardsmasterserver.matches;

import com.etherblood.cardsmasterserver.matches.internal.players.HumanPlayer;
import com.etherblood.cardsmasterserver.cards.CardCollectionService;
import com.etherblood.cardsmasterserver.matches.internal.MatchContextWrapper;
import com.etherblood.cardsmasterserver.matches.internal.MatchResult;
import com.etherblood.cardsmatch.cardgame.UpdateBuilder;
import com.etherblood.cardsmasterserver.matches.internal.players.AbstractPlayer;
import com.etherblood.cardsmasterserver.matches.internal.players.AiPlayer;
import com.etherblood.cardsmasterserver.network.connections.UserConnectionService;
import com.etherblood.cardsmasterserver.network.events.UserLogoutEvent;
import com.etherblood.cardsmasterserver.network.messages.MessageHandler;
import com.etherblood.cardsmasterserver.system.SystemTaskEvent;
import com.etherblood.cardsmasterserver.users.UserService;
import com.etherblood.cardsmatch.cardgame.IllegalCommandException;
import com.etherblood.cardsmatch.cardgame.client.SystemsEventHandler;
import com.etherblood.cardsnetworkshared.master.commands.MatchRequest;
import com.etherblood.cardsnetworkshared.match.commands.TriggerEffectRequest;
import com.etherblood.cardsnetworkshared.DefaultMessage;
import com.etherblood.cardsnetworkshared.match.misc.MatchUpdate;
import com.etherblood.entitysystem.data.EntityComponentMapReadonly;
import com.etherblood.eventsystem.GameEvent;
import com.etherblood.eventsystem.GameEventHandler;
import com.etherblood.cardsmatch.cardgame.match.MatchContext;
import com.etherblood.cardsmatch.cardgame.match.PlayerDefinition;
import com.etherblood.cardsmatch.cardgame.match.RulesDefinition;
import com.etherblood.cardsmatch.cardgame.client.SystemsEventHandlerDispatcher;
import com.etherblood.cardsnetworkshared.match.updates.JoinedMatchUpdate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Random;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicReference;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
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
    private final AtomicReference<Long> pendingUserId = new AtomicReference<>(null);
    private HashMap<String, RulesDefinition> rules;

//    private Map<Class, UpdateBuilder> updateBuilders;
    @Autowired
    private UserConnectionService connectionService;
//    @Autowired
//    private CardTemplatesService templateService;
    @Autowired
    private CardCollectionService collectionService;
    @Autowired
    private UserService userService;
    @Autowired
    private ApplicationEventPublisher eventPublisher;

//    @PostConstruct
//    @PreAuthorize("denyAll")
//    public void initRules() {
//        updateBuilders = ClientUpdaterFactory.createUpdateBuilders();
//    }
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
                ex.printStackTrace(System.out);
            }
        }
    }

    @MessageHandler
    @PreAuthorize("hasRole('ROLE_USER')")
    public void onTriggerEffectRequest(TriggerEffectRequest triggerEffect) {
        HumanPlayer matchPlayer = matchMap.get(connectionService.getCurrentUserId());
        MatchContextWrapper match = matchPlayer.getMatch();
        try {
            matchPlayer.triggerEffect(triggerEffect.getSource(), triggerEffect.getTargets());
            updateMatch(match);
        } catch (IllegalCommandException e) {
            e.printStackTrace(System.err);
        } catch (Exception e) {
            cleanupMatchAfterException(e, match);
        }
    }

    @MessageHandler
    @PreAuthorize("hasRole('ROLE_USER')")
    public void onMatchRequest(MatchRequest matchRequest) {
        //TODO: make sure a user can only be once in the matchQueue/an active match
        //duplicate matchRequests should be discarded as should requests during a match
        Long currentUserId = connectionService.getCurrentUserId();
        if (matchRequest.isVersusBot()) {
            startRuleMatch(currentUserId, null);
        } else {
            while (!pendingUserId.compareAndSet(null, currentUserId)) {
                Long matchPartnerId = pendingUserId.getAndSet(null);
                if (matchPartnerId != null) {
                    startRuleMatch(matchPartnerId, currentUserId);
                    break;
                }
            }
        }
    }

    @EventListener
    @PreAuthorize("hasRole('ROLE_SYSTEM')")
    public void onUserLogout(UserLogoutEvent event) {
        pendingUserId.compareAndSet(event.getUserId(), null);
    }

    private void startRuleMatch(long user1, Long user2) {
        RulesDefinition ruleset = rules.get("default rules");//new DefaultRulesDef(templateService.getAll());
        ArrayList<PlayerDefinition> playerDefs = new ArrayList<>();
        ArrayList<AbstractPlayer> players = new ArrayList<>();
        ArrayList<HumanPlayer> humans = new ArrayList<>();

        PlayerDefinition def1 = createHumanPlayerDefinition(user1, createBotLibrary(ruleset.getTemplateNames()));
        playerDefs.add(def1);

        PlayerDefinition def2;
        if (user2 != null) {
            def2 = createHumanPlayerDefinition(user2, createBotLibrary(ruleset.getTemplateNames()));
        } else {
            def2 = createBotPlayerDefinition(createBotLibrary(ruleset.getTemplateNames()));
        }
        playerDefs.add(def2);

//        final EntityComponentMap data = rules.getBuilder().removeBean(EntityComponentMap.class);
//        rules.getBuilder().addBean(new VersionedEntityComponentMapImpl(data));
        final MatchContext context = ruleset.start(playerDefs);

        HumanPlayer player1 = new HumanPlayer(user1, def1.getEntity());
        player1.setConverter(def1.getConverter());
        humans.add(player1);
        players.add(player1);

        AbstractPlayer player2;
        if (user2 != null) {
            player2 = new HumanPlayer(user2, def2.getEntity());
            ((HumanPlayer) player2).setConverter(def2.getConverter());
            humans.add((HumanPlayer) player2);
        } else {
            AiPlayer aiPlayer = new AiPlayer(def2.getEntity());
            aiPlayer.setBot(def2.getBotInstance());
            player2 = aiPlayer;
        }
        players.add(player2);

        final MatchContextWrapper wrapper = new MatchContextWrapper();
        wrapper.init(context, ruleset.getUpdateBuilders(), players);

        SystemsEventHandlerDispatcher dispatcher = context.getBean(SystemsEventHandlerDispatcher.class);
        List<SystemsEventHandler> handlers = dispatcher.getHandlers();
        final EntityComponentMapReadonly data = context.getBean(EntityComponentMapReadonly.class);
        for (final HumanPlayer human : humans) {
            handlers.add(new SystemsEventHandler() {
                @Override
                public <T extends GameEvent> void onEvent(Class<GameEventHandler<T>> systemClass, T gameEvent) {
                    UpdateBuilder<MatchUpdate, T> updateBuilder = wrapper.getUpdateBuilders().get(systemClass);
                    if (updateBuilder != null) {
                        human.send(updateBuilder.build(data, human.getConverter(), gameEvent));
                    }
                }
            });
            human.getConverter().register(human.getPlayer());
            for (AbstractPlayer player : players) {
                if (player != human) {
                    human.getConverter().register(player.getPlayer());
                }
            }
        }

//        for (AbstractPlayer player : players) {
//            if (player instanceof AiPlayer) {
//                AiPlayer ai = (AiPlayer) player;
//
////                EndTurnCommandFactory endTurnCommandFactory = new EndTurnCommandFactory();
////                endTurnCommandFactory.data = wrapper.getData();
////                ai.setBot(new EndTurnBot(endTurnCommandFactory));
//                MatchContext simulationContext = new DefaultRulesDef(templateService.getAll()).getBuilder().build();
//                MonteCarloController monteCarloBot = new MonteCarloController(context, simulationContext, new CommandGeneratorImpl(), player1.getPlayer());
//                ai.setBot(monteCarloBot);
//            }
//        }
        ruleset.flush(context);
        assert wrapper.getCurrentPlayer() != null;
        for (HumanPlayer human : humans) {
            matchMap.put(human.getUserId(), human);
            human.send(new JoinedMatchUpdate());
        }
        updateMatch(wrapper);
    }

    private PlayerDefinition createHumanPlayerDefinition(long userAccountId, String[] library) {
        PlayerDefinition def = new PlayerDefinition();
        def.setLibrary(library);
        def.setHeroTemplate("Hero");
        def.setName(userService.getUser(userAccountId).getUsername());
        def.setBot(false);

        return def;
    }

    private PlayerDefinition createBotPlayerDefinition(String[] library) {
        PlayerDefinition def = new PlayerDefinition();
        def.setLibrary(library);
        def.setHeroTemplate("Hero");
        def.setName("Bot");
        def.setBot(true);
        return def;
    }

    @EventListener
    @PreAuthorize("hasRole('ROLE_SYSTEM')")
    public void aiUpdate(AiUpdateRequest request) {
        MatchContextWrapper matchWrapper = request.getMatchWrapper();
        AbstractPlayer currentPlayer = matchWrapper.getCurrentPlayer();
        if (currentPlayer instanceof AiPlayer && !matchWrapper.hasMatchEnded()) {
            try {
                ((AiPlayer) currentPlayer).compute();
                updateMatch(matchWrapper);
            } catch (Exception e) {
                cleanupMatchAfterException(e, matchWrapper);
            }
        }
    }

    private void cleanupMatchAfterException(Exception e, MatchContextWrapper matchWrapper) {
        e.printStackTrace(System.err);
        System.err.println("ending match because of exception");
        //TODO: end match with exception result
        for (HumanPlayer player : matchWrapper.getPlayers(HumanPlayer.class)) {
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
        MatchResult result = matchWrapper.getResult();
        for (AbstractPlayer player : result.getWinners()) {
            if (player instanceof HumanPlayer) {
                eventPublisher.publishEvent(new SystemTaskEvent(new WonMatchEvent((HumanPlayer) player)));
            }
        }
        for (HumanPlayer matchPlayer : matchWrapper.getPlayers(HumanPlayer.class)) {
            matchMap.remove(matchPlayer.getUserId());
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

    private String[] createBotLibrary(List<String> templates) {
//        String[] COLLECTIBLE_TEMPLATES = templateService.getCollectibles();
        Random rng = new Random();
        String[] library = new String[30];
        for (int i = 0; i < library.length; i++) {
            library[i] = templates.get(rng.nextInt(templates.size()));//COLLECTIBLE_TEMPLATES[rng.nextInt(COLLECTIBLE_TEMPLATES.length)];
        }
        for (int i = 0; i < 5; i++) {
            library[i] = "Warsong Commander";
        }
        for (int i = 5; i < 10; i++) {
            library[i] = "Grim Patron";
        }
        for (int i = 10; i < 15; i++) {
            library[i] = "Whirlwind";
        }
        return library;
    }
}
