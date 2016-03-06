/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.etherblood.cardsmatch.cardgame.events.endTurn.systems;

import com.etherblood.cardsmatch.cardgame.AbstractMatchSystem;
import com.etherblood.match.Autowire;
import com.etherblood.cardsmatch.cardgame.components.player.NextTurnPlayerComponent;
import com.etherblood.cardsmatch.cardgame.events.endTurn.EndTurnEvent;
import com.etherblood.cardsmatch.cardgame.events.startTurn.StartTurnEvent;
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
        if(data.has(event.player, NextTurnPlayerComponent.class)) {
            enqueueEvent(new StartTurnEvent(data.get(event.player, NextTurnPlayerComponent.class).player));
        } else {
            throw new RuntimeException("Failed to assign next turn because " + NextTurnPlayerComponent.class.getSimpleName() + " is missing.");
        }
        return event;
    }
    
}
