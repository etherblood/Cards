package com.etherblood.eventsystem;

import java.util.ArrayList;
import java.util.HashMap;

public final class GameEventDispatcherImpl implements GameEventDispatcher {

    private final HashMap<Class, ArrayList<GameEventHandler>> handlersMap = new HashMap<>();

    @Override
    public void dispatch(GameEvent event) {
        ArrayList<GameEventHandler> handlers = handlersMap.get(event.getClass());
        if (handlers == null) {
            return;
        }
        for (GameEventHandler handler : handlers) {
            event = handler.handle(event);
            if (event == null) {
                return;
            }
        }
    }

    @Override
    public void subscribe(Class eventClass, GameEventHandler handler) {
        ArrayList<GameEventHandler> handlers = handlersMap.get(eventClass);
        if (handlers == null) {
            handlers = new ArrayList<>();
            handlersMap.put(eventClass, handlers);
        }
        handlers.add(handler);
    }

    @Override
    public void unsubscribe(Class eventClass, GameEventHandler handler) {
        ArrayList<GameEventHandler> handlers = handlersMap.get(eventClass);
        if (handlers != null) {
            handlers.remove(handler);
            if (handlers.isEmpty()) {
                handlersMap.remove(eventClass);
            }
        }
    }
}