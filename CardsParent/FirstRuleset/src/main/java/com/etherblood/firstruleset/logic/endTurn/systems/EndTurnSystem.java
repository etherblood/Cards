/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.etherblood.firstruleset.logic.endTurn.systems;

import com.etherblood.cardsmatch.cardgame.AbstractMatchSystem;
import com.etherblood.firstruleset.logic.endTurn.ApplyEndTurnEvent;
import com.etherblood.firstruleset.logic.endTurn.EndTurnEvent;

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
