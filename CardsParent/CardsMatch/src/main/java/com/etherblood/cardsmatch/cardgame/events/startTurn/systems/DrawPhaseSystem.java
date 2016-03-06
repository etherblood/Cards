/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.etherblood.cardsmatch.cardgame.events.startTurn.systems;

import com.etherblood.cardsmatch.cardgame.AbstractMatchSystem;
import com.etherblood.cardsmatch.cardgame.events.draw.RequestDrawEvent;
import com.etherblood.cardsmatch.cardgame.events.startTurn.StartTurnEvent;

/**
 *
 * @author Philipp
 */
public class DrawPhaseSystem extends AbstractMatchSystem<StartTurnEvent> {

    @Override
    public StartTurnEvent handle(StartTurnEvent event) {
        enqueueEvent(new RequestDrawEvent(event.player));
        return event;
    }
}
