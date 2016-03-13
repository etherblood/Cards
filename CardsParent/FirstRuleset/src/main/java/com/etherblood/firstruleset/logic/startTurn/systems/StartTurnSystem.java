/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.etherblood.firstruleset.logic.startTurn.systems;

import com.etherblood.cardsmatch.cardgame.AbstractMatchSystem;
import com.etherblood.cardsmatch.cardgame.match.Autowire;
import com.etherblood.cardsmatch.cardgame.components.player.ItsMyTurnComponent;
import com.etherblood.firstruleset.logic.startTurn.StartTurnEvent;
import com.etherblood.entitysystem.data.EntityComponentMap;

/**
 *
 * @author Philipp
 */
public class StartTurnSystem extends AbstractMatchSystem<StartTurnEvent> {
    @Autowire
    private EntityComponentMap data;

    @Override
    public StartTurnEvent handle(StartTurnEvent event) {
        data.set(event.player, new ItsMyTurnComponent());
        return event;
    }
    
}
