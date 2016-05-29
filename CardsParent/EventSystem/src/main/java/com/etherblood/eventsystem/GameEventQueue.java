package com.etherblood.eventsystem;

import java.util.List;

/**
 *
 * @author Philipp
 */
public interface GameEventQueue {
    void fireEvent(GameEvent event);
    void fireEvents(List<GameEvent> event);
}
