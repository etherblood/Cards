package com.etherblood.cardsmatch.cardgame;

import com.etherblood.cardscontext.Autowire;
import com.etherblood.eventsystem.GameEvent;
import com.etherblood.eventsystem.GameEventDispatcher;
import com.etherblood.eventsystem.GameEventHandler;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 *
 * @author Philipp
 */
public class MatchGameEventDispatcher implements GameEventDispatcher {

    private final HashMap<Class, ArrayList<GameEventHandler>> handlersMap = new HashMap<>();
    @Autowire
    private List<GlobalEventHandler> globalEventHandlers;

    @Override
    public void dispatch(GameEvent event) {
        ArrayList<GameEventHandler> handlers = handlersMap.get(event.getClass());
        if (handlers == null) {
            return;
        }
        for (GameEventHandler handler : handlers) {
            event = handleEvent(handler, event);
            if (event == null) {
                return;
            }
        }
    }

    private <T extends GameEvent> T handleEvent(GameEventHandler<T> handler, T event) {
        T result = handler.handle(event);
        for (GlobalEventHandler globalHandler : globalEventHandlers) {
            globalHandler.onEvent((Class<GameEventHandler<T>>) handler.getClass(), event);
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
        if (handlers.isEmpty()) {
            handlersMap.remove(eventClass);
        }
    }

    private ArrayList<GameEventHandler> createGetHandlers(Class eventClass) {
        ArrayList<GameEventHandler> handlers = handlersMap.get(eventClass);
        if (handlers == null) {
            handlers = new ArrayList<>();
            handlersMap.put(eventClass, handlers);
        }
        return handlers;
    }

    public List<GlobalEventHandler> getGlobalEventHandlers() {
        return globalEventHandlers;
    }
}
