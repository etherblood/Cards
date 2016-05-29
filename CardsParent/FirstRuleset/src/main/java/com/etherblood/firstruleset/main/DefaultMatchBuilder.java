package com.etherblood.firstruleset.main;

import com.etherblood.cardscontext.CardsContext;
import com.etherblood.cardsmatch.cardgame.MatchGameEventDispatcher;
import com.etherblood.cardsmatch.cardgame.NetworkPlayer;
import com.etherblood.cardsmatch.cardgame.UpdateBuilder;
import com.etherblood.cardsmatch.cardgame.bot.Bot;
import com.etherblood.cardsmatch.cardgame.components.misc.NameComponent;
import com.etherblood.cardsmatch.cardgame.components.player.PlayerComponent;
import com.etherblood.cardsmatch.cardgame.events.gamestart.GameStartEvent;
import com.etherblood.cardsmatchapi.BotProxy;
import com.etherblood.cardsmatchapi.HumanProxy;
import com.etherblood.cardsmatchapi.MatchBuilder;
import com.etherblood.cardsmatchapi.PlayerDefinition;
import com.etherblood.cardsmatchapi.SpectatorProxy;
import com.etherblood.cardsmatchapi.StateProxy;
import com.etherblood.cardsnetworkshared.match.commands.TriggerEffectRequest;
import com.etherblood.cardsnetworkshared.match.misc.MatchUpdate;
import com.etherblood.entitysystem.data.EntityComponentMap;
import com.etherblood.entitysystem.data.EntityId;
import com.etherblood.entitysystem.data.EntityIdFactory;
import com.etherblood.eventsystem.GameEvent;
import com.etherblood.eventsystem.GameEventHandler;
import com.etherblood.eventsystem.GameEventQueue;
import com.etherblood.eventsystem.GameEventQueueImpl;
import com.etherblood.firstruleset.ClientUpdaterFactory;
import com.etherblood.firstruleset.CommandHandlerImpl;
import com.etherblood.firstruleset.IdConverterExtendedImpl;
import com.etherblood.firstruleset.bot.CommandGeneratorImpl;
import com.etherblood.cardsmatch.cardgame.bot.monteCarlo.MonteCarloController;
import com.etherblood.firstruleset.logic.cardZones.events.BoardAttachEvent;
import com.etherblood.firstruleset.logic.cardZones.events.LibraryAttachEvent;
import com.etherblood.firstruleset.logic.draw.RequestDrawEvent;
import com.etherblood.firstruleset.logic.entities.AttachTemplateEvent;
import com.etherblood.firstruleset.logic.player.NextTurnPlayerComponent;
import com.etherblood.firstruleset.logic.shuffle.ShuffleLibraryEvent;
import com.etherblood.firstruleset.logic.startTurn.StartTurnEvent;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Random;
import com.etherblood.cardsmatch.cardgame.GlobalEventHandler;

/**
 *
 * @author Philipp
 */
public class DefaultMatchBuilder implements MatchBuilder<TriggerEffectRequest, MatchUpdate>{
    private final CardsContext context;
    private final ContextFactory contextFactory;
    private final List<AbstractPlayerProxy> players = new ArrayList<>();
    private final SpectatorProxy spectator = new SpectatorProxyImpl();
    private final StateProxyImpl stateProxy;

    public DefaultMatchBuilder(ContextFactory contextFactory, CardsContext context) {
        this.contextFactory = contextFactory;
        this.context = context;
        stateProxy = new StateProxyImpl(context, players);
    }
    
    @Override
    public HumanProxy<TriggerEffectRequest, MatchUpdate> createHuman(PlayerDefinition def) {
        HumanProxyImpl proxy = new HumanProxyImpl(context, def);
        players.add(proxy);
        return proxy;
    }

    @Override
    public BotProxy createBot(PlayerDefinition def) {
        BotProxyImpl proxy = new BotProxyImpl(context, def);
        players.add(proxy);
        return proxy;
    }

    @Override
    public SpectatorProxy<MatchUpdate> getSpectator() {
        return spectator;
    }

    @Override
    public void start() {
        if(players.size() != 2) {
            throw new IllegalStateException();
        }
        EntityComponentMap data = context.getBean(EntityComponentMap.class);
        EntityIdFactory idFactory = context.getBean(EntityIdFactory.class);
        CommandHandlerImpl commandHandler = context.getBean(CommandHandlerImpl.class);
        MatchGameEventDispatcher dispatcher = context.getBean(MatchGameEventDispatcher.class);
        List<GlobalEventHandler> handlers = dispatcher.getGlobalEventHandlers();
        final HashMap<Class, UpdateBuilder> updateBuilders = ClientUpdaterFactory.createUpdateBuilders();

        for (AbstractPlayerProxy player : players) {
            player.setEntity(idFactory.createEntity());
        }
        for (AbstractPlayerProxy player : players) {
            EntityId playerEntity = player.getEntity();
            PlayerDefinition def = player.getPlayerDefinition();
            initPlayer(context, playerEntity, def.getName(), def.getHeroTemplate(), def.getLibrary());
            if (player instanceof BotProxyImpl) {
                BotProxyImpl botProxy = (BotProxyImpl)player;
                Bot bot = new MonteCarloController(context, contextFactory.buildContext(), new CommandGeneratorImpl(), players.get(0).getEntity());
                botProxy.setBot(bot);
                commandHandler.registerBot(bot);
            } else if (player instanceof HumanProxyImpl) {
                final HumanProxyImpl humanProxy = (HumanProxyImpl) player;
                IdConverterExtendedImpl converter = new IdConverterExtendedImpl(data, new NetworkPlayer<MatchUpdate>() {
                    @Override
                    public void send(MatchUpdate message) {
                        //TODO: remove this, idConverter should not send anything
                        humanProxy.getTotalUpdates().add(message);
                    }
                });
                handlers.add(new GlobalEventHandler() {
                    @Override
                    public <T extends GameEvent> void onEvent(Class<GameEventHandler<T>> systemClass, T gameEvent) {
                        UpdateBuilder<MatchUpdate, T> updateBuilder = updateBuilders.get(systemClass);
                        if (updateBuilder != null) {
                            humanProxy.getTotalUpdates().add(updateBuilder.build(data, converter, gameEvent));
                        }
                    }
                });
                converter.register(player.getEntity());
                for (AbstractPlayerProxy otherPlayer : players) {
                    if (!player.getEntity().equals(otherPlayer.getEntity())) {
                        converter.register(otherPlayer.getEntity());
                    }
                }
                humanProxy.setIdConverter(converter);
            }
        }
        
        //TODO: init spectator

        EntityId player1 = players.get(0).getEntity();
        EntityId player2 = players.get(1).getEntity();

        data.set(player1, new NextTurnPlayerComponent(player2));
        data.set(player2, new NextTurnPlayerComponent(player1));

        GameEventQueueImpl eventQueue = context.getBean(GameEventQueueImpl.class);
        eventQueue.fireEvent(new GameStartEvent());

        for (int i = 0; i < 3; i++) {
            eventQueue.fireEvent(new RequestDrawEvent(player2));
            eventQueue.fireEvent(new RequestDrawEvent(player1));
        }
        Random rng = new Random();
        EntityId startingPlayer = rng.nextBoolean() ? player1 : player2;
        eventQueue.fireEvent(new RequestDrawEvent(startingPlayer == player1 ? player2 : player1));
        eventQueue.fireEvent(new StartTurnEvent(startingPlayer));
        eventQueue.handleEvents();
    }
    
    private void initPlayer(CardsContext match, EntityId player, String name, String heroTemplate, List<String> library) {
        EntityComponentMap data = match.getBean(EntityComponentMap.class);
        EntityIdFactory idFactory = match.getBean(EntityIdFactory.class);
        GameEventQueue events = match.getBean(GameEventQueue.class);

        data.set(player, new NameComponent(name));
        data.set(player, new PlayerComponent());

        EntityId hero = idFactory.createEntity();
        events.fireEvent(new AttachTemplateEvent(hero, heroTemplate, player, null));
        events.fireEvent(new BoardAttachEvent(hero));

        for (String template : library) {
            EntityId cardId = idFactory.createEntity();
            events.fireEvent(new AttachTemplateEvent(cardId, template, player, null));
            events.fireEvent(new LibraryAttachEvent(cardId));
        }
        events.fireEvent(new ShuffleLibraryEvent(player));
    }

    @Override
    public StateProxy getStateTracker() {
        return stateProxy;
    }

}
