package com.etherblood.cardsjmeclient.events;

/**
 *
 * @author Philipp
 */
public interface EventListener<T> {
    void onEvent(T event);
}
