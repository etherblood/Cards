/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.etherblood.cardsmatch.cardgame.events.surrender.systems;

import com.etherblood.cardsmatch.cardgame.AbstractMatchSystem;
import com.etherblood.cardsmatch.cardgame.events.gameover.PlayerLostEvent;
import com.etherblood.cardsmatch.cardgame.events.surrender.SurrenderEvent;

/**
 *
 * @author Philipp
 */
public class SurrenderSystem extends AbstractMatchSystem<SurrenderEvent> {

    @Override
    public SurrenderEvent handle(SurrenderEvent event) {
        enqueueEvent(new PlayerLostEvent(event.player));
        return event;
    }
    
}
