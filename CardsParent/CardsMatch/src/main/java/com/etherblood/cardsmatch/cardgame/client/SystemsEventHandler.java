package com.etherblood.cardsmatch.cardgame.client;

import com.etherblood.eventsystem.GameEvent;
import com.etherblood.eventsystem.GameEventHandler;

/**
 *
 * @author Philipp
 */
public interface SystemsEventHandler {
    <T extends GameEvent> void onEvent(Class<GameEventHandler<T>> systemClass, T gameEvent);
}
