package com.etherblood.cardsmatch.cardgame;

import com.etherblood.eventsystem.GameEvent;
import com.etherblood.eventsystem.GameEventHandler;

/**
 *
 * @author Philipp
 */
public interface GlobalEventHandler {
    <T extends GameEvent> void onEvent(Class<GameEventHandler<T>> systemClass, T gameEvent);
}
