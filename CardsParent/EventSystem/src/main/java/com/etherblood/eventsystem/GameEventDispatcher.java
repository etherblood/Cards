package com.etherblood.eventsystem;

/**
 *
 * @author Philipp
 */
public interface GameEventDispatcher {
    void subscribe(Class eventClass, GameEventHandler handler);
    void unsubscribe(Class eventClass, GameEventHandler handler);
    void dispatch(GameEvent event);
}
