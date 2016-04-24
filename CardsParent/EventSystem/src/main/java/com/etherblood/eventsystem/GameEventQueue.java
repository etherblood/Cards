package com.etherblood.eventsystem;

/**
 *
 * @author Philipp
 */
public interface GameEventQueue {
    void fireEvent(GameEvent event);
}
