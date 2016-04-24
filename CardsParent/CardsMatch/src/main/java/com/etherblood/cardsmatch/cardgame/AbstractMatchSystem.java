package com.etherblood.cardsmatch.cardgame;

import com.etherblood.cardscontext.Autowire;
import com.etherblood.eventsystem.GameEvent;
import com.etherblood.eventsystem.GameEventHandler;
import com.etherblood.eventsystem.GameEventQueueImpl;

/**
 *
 * @author Philipp
 */
public abstract class AbstractMatchSystem<E extends GameEvent> implements GameEventHandler<E> {
    @Autowire
    protected GameEventQueueImpl events;
    
    public void enqueueEvent(GameEvent event) {
        events.fireEvent(event);
    }
    
}
