package com.etherblood.cardsjmeclient.events;

/**
 *
 * @author Philipp
 */
public interface Eventbus {
    <T> void subscribe(Class<T> eventClass, EventListener<T> listener);
    <T> void unsubscribe(Class<T> eventClass, EventListener<T> listener);
    void sendEvent(Object event);
}
