/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.etherblood.cardsmatch.cardgame;

import com.etherblood.cardsmatch.cardgame.match.Autowire;
import com.etherblood.eventsystem.GameEvent;
import com.etherblood.eventsystem.GameEventDataStack;
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
    
    public GameEventDataStack eventData() {
        return events.getDataStack();
    }
    
}
