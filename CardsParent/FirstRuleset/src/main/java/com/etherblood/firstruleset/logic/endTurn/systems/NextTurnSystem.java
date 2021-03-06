/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.etherblood.firstruleset.logic.endTurn.systems;

import com.etherblood.cardsmatch.cardgame.AbstractMatchSystem;
import com.etherblood.cardscontext.Autowire;
import com.etherblood.firstruleset.logic.player.NextTurnPlayerComponent;
import com.etherblood.firstruleset.logic.endTurn.EndTurnEvent;
import com.etherblood.firstruleset.logic.startTurn.StartTurnEvent;
import com.etherblood.entitysystem.data.EntityComponentMapReadonly;

/**
 *
 * @author Philipp
 */
public class NextTurnSystem extends AbstractMatchSystem<EndTurnEvent> {
    @Autowire
    private EntityComponentMapReadonly data;
    
    @Override
    public EndTurnEvent handle(EndTurnEvent event) {
        enqueueEvent(new StartTurnEvent(data.get(event.player, NextTurnPlayerComponent.class).player));
        return event;
    }
    
}
