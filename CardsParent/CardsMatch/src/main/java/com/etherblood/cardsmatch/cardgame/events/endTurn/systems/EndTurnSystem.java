/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.etherblood.cardsmatch.cardgame.events.endTurn.systems;

import com.etherblood.cardsmatch.cardgame.AbstractMatchSystem;
import com.etherblood.cardsmatch.cardgame.events.endTurn.ApplyEndTurnEvent;
import com.etherblood.cardsmatch.cardgame.events.endTurn.EndTurnEvent;

/**
 *
 * @author Philipp
 */
public class EndTurnSystem extends AbstractMatchSystem<EndTurnEvent> {

    @Override
    public EndTurnEvent handle(EndTurnEvent event) {
        enqueueEvent(new ApplyEndTurnEvent(event.player));
        return event;
    }
    
}
