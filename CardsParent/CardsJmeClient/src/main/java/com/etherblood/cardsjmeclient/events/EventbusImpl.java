package com.etherblood.cardsjmeclient.events;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class EventbusImpl implements Eventbus {

    private final HashMap<Class, List<EventListener>> classListeners = new HashMap<>();

    @Override
    public <T> void subscribe(Class<T> eventClass, EventListener<T> listener) {
        if(eventClass.isInterface()) {
            throw new UnsupportedOperationException("interfaces not supported yet. " + eventClass.getName());
        }
        List<EventListener> list = classListeners.get(eventClass);
        if (list == null) {
            list = new ArrayList<>();
            classListeners.put(eventClass, list);
        }
        list.add(listener);
    }

    @Override
    public <T> void unsubscribe(Class<T> eventClass, EventListener<T> listener) {
        if(eventClass.isInterface()) {
            throw new UnsupportedOperationException("interfaces not supported yet");
        }
        List<EventListener> list = classListeners.get(eventClass);
        list.remove(listener);
        if (list.isEmpty()) {
            classListeners.remove(eventClass);
        }
    }

    @Override
    public void sendEvent(Object event) {
        Class eventClass = event.getClass();
        boolean listenerFound = false;
        while (eventClass != null) {
            listenerFound |= notifyListenersForClass(eventClass, event);
            eventClass = eventClass.getSuperclass();
        }
        if (!listenerFound) {
            System.out.println("WARNING: no listener found for event " + event);
        }
    }

    private boolean notifyListenersForClass(Class eventClass, Object event) {
        List<EventListener> list = classListeners.get(eventClass);
        if (list == null) {
            return false;
        }
        for (EventListener eventListener : list) {
            eventListener.onEvent(event);
        }
        return true;
    }

}
