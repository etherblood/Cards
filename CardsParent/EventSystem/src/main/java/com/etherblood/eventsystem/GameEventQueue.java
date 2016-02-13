package com.etherblood.eventsystem;

/**
 *
 * @author Philipp
 */
public interface GameEventQueue {
//    void handleEvents();
    void fireEvent(GameEvent event);
}
