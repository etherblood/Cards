/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.etherblood.cardsmatch.cardgame;

import com.etherblood.cardsmatch.cardgame.bot.commands.AttackCommandFactory;
import com.etherblood.cardsmatch.cardgame.bot.commands.CommandManager;
import com.etherblood.cardsmatch.cardgame.bot.commands.EndTurnCommandFactory;
import com.etherblood.cardsmatch.cardgame.bot.commands.SummonCommandFactory;
import com.etherblood.cardsmatch.cardgame.client.SystemsEventHandler;
import com.etherblood.cardsmatch.cardgame.components.player.PlayerComponent;
import com.etherblood.entitysystem.data.EntityComponentMapImpl;
import com.etherblood.entitysystem.data.EntityId;
import com.etherblood.entitysystem.data.EntityIdFactory;
import com.etherblood.entitysystem.data.IncrementalEntityIdFactory;
import com.etherblood.entitysystem.version.VersionedEntityComponentMap;
import com.etherblood.entitysystem.version.VersionedEntityComponentMapImpl;
import com.etherblood.eventsystem.GameEvent;
import com.etherblood.eventsystem.GameEventQueueImpl;
import java.util.Set;

/**
 *
 * @author Philipp
 */
public class MatchState {
    private final MatchContext context = new MatchContext();
    
    public final EntityIdFactory idFactory = new IncrementalEntityIdFactory();
    public final VersionedEntityComponentMap data = new VersionedEntityComponentMapImpl(new EntityComponentMapImpl());
    public RngFactory rng = new RngFactoryImpl();
    public final TemplateSet templates;
    
//    public final ClientStateHandlerImpl clients = new ClientStateHandlerImpl();
//    public final MatchGameEventDispatcher dispatcher = new MatchGameEventDispatcher(new ExtendedEventHandlerTrackerImpl(clients));
    public final SystemsEventHandler eventLogger;
    public final MatchGameEventDispatcher dispatcher = new MatchGameEventDispatcher();
    public final GameEventQueueImpl events = new GameEventQueueImpl(dispatcher);
//    public final ArrayList<EntityId> playerIds = new ArrayList<>();
//    public final Map<Class, AbstractEventValidator> validators = new HashMap<>();

    public MatchState(TemplateSet templates, SystemsEventHandler eventLogger) {
        this.templates = templates;
        this.eventLogger = eventLogger;
//        for (Class validatorClass : new generatedValidators.GeneratedValidatorClass().classes) {
//            try {
//                validators.put(validatorClass.getAnnotation(EventValidator.class).annotationType(), (AbstractEventValidator) validatorClass.newInstance());
//            } catch (InstantiationException | IllegalAccessException ex) {
//                Logger.getLogger(MatchState.class.getName()).log(Level.SEVERE, null, ex);
//            }
//        }
//        dispatcher.setEventHandlerTracker(eventLogger);
        
        context.addBean(dispatcher);
        context.addBean(eventLogger);
        context.addBean(data);
        context.addBean(events);
        context.addBean(events.getDataStack());
        context.addBean(idFactory);
        context.addBean(rng);
        context.addBean(templates);
        CommandManager commandManager = new CommandManager();
        EndTurnCommandFactory endTurnCommandFactory = new EndTurnCommandFactory();
        SummonCommandFactory summonCommandFactory = new SummonCommandFactory();
        AttackCommandFactory attackCommandFactory = new AttackCommandFactory();
        context.addBean(commandManager);
        context.addBean(endTurnCommandFactory);
        context.addBean(summonCommandFactory);
        context.addBean(attackCommandFactory);
        
        context.repopulateAll();
    }

    public <E extends GameEvent> void addSystem(Class<E> eventClass, AbstractMatchSystem<E> system) {
        dispatcher.subscribe(eventClass, system);
//        system.match = this;
//        system.data = data;
//        system.setEvents(events);
//        system.setIdFactory(idFactory);
//        system.rng = rng;
//        system.templates = templates;
        context.populate(system);
//        system.setClients(clients);
    }
    
//    protected <E extends GameEvent> void addUpdateHandler(Class<? extends GameEventHandler<E>> systemClass, EventToUpdateHandler<E> handler) {
//        dispatcher.getEventHandlerTracker().subscribe(systemClass, handler);
//    }

//    public void tryEvent(EntityId playerId, GameEvent gameEvent) {
//        AbstractEventValidator validator = validators.get(gameEvent.getClass());
//        if(validator != null) {
//            validator.validate(playerId, gameEvent);
//        }
//        events.enqueueEvent(gameEvent);
//        events.handleEvents();
//    }
    
    public Set<EntityId> playerIds() {
        return data.entities(PlayerComponent.class);
    }
    
//    }

    //    protected void updateClients(MatchUpdate update) {
    //        clients.handle(update);
    public MatchContext getContext() {
        return context;
    }
}
