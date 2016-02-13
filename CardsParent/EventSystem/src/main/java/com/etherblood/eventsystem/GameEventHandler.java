package com.etherblood.eventsystem;

/**
 *
 * @author Philipp
 * @param <E>
 */
public interface GameEventHandler<E extends GameEvent> {
    E handle(E event);
}
