package com.etherblood.cardsmatch.cardgame;

import com.etherblood.cardsmatch.cardgame.client.SystemsEventHandler;
import com.etherblood.eventsystem.GameEvent;
import com.etherblood.eventsystem.GameEventDispatcher;
import com.etherblood.eventsystem.GameEventHandler;
import java.util.ArrayList;
import java.util.HashMap;

/**
 *
 * @author Philipp
 */
public class MatchGameEventDispatcher implements GameEventDispatcher {
    private final HashMap<Class, ArrayList<GameEventHandler>> handlersMap = new HashMap<>();
    @Autowire
    private SystemsEventHandler systemsEventHandler = null;

    @Override
    public void dispatch(GameEvent event) {
        ArrayList<GameEventHandler> handlers = handlersMap.get(event.getClass());
        if(handlers == null) {
            return;
        }
        for (GameEventHandler handler : handlers) {
            event = handleEvent(handler, event);
            if(event == null) {
                return;
            }
        }
    }

    private GameEvent handleEvent(GameEventHandler handler, GameEvent event) {
//        try {
            GameEvent result = handler.handle(event);
//        } catch(Exception ex) {
//            //TODO: add interface which makes rollbacks possible?
//            Logger.getLogger(GameEventDispatcherImpl.class.getName()).log(Level.SEVERE, null, ex);
//        }
        if(systemsEventHandler != null && systemsEventHandler.isEnabled()) {
            systemsEventHandler.onEvent(handler.getClass(), event);
        }
        return result;
    }

    @Override
    public void subscribe(Class eventClass, GameEventHandler handler) {
        createGetHandlers(eventClass).add(handler);
    }

    @Override
    public void unsubscribe(Class eventClass, GameEventHandler handler) {
        ArrayList<GameEventHandler> handlers = createGetHandlers(eventClass);
        handlers.remove(handler);
        if(handlers.isEmpty()) {
            handlersMap.remove(eventClass);
        }
    }
    
    private ArrayList<GameEventHandler> createGetHandlers(Class eventClass) {
        ArrayList<GameEventHandler> handlers = handlersMap.get(eventClass);
        if(handlers == null) {
            handlers = new ArrayList<GameEventHandler>();
            handlersMap.put(eventClass, handlers);
        }
        return handlers;
    }

    public SystemsEventHandler getEventHandlerTracker() {
        return systemsEventHandler;
    }

    public void setEventHandlerTracker(SystemsEventHandler eventHandlerTracker) {
        this.systemsEventHandler = eventHandlerTracker;
    }
}
