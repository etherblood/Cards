/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.etherblood.firstruleset.logic.cardZones.systems;

import com.etherblood.cardsmatch.cardgame.AbstractMatchSystem;
import com.etherblood.cardscontext.Autowire;
import com.etherblood.firstruleset.logic.cardZones.components.BoardCardComponent;
import com.etherblood.firstruleset.logic.cardZones.events.BoardDetachEvent;
import com.etherblood.entitysystem.data.EntityComponentMap;

/**
 *
 * @author Philipp
 */
public class BoardDetachSystem extends AbstractMatchSystem<BoardDetachEvent> {
    @Autowire
    private EntityComponentMap data;

    @Override
    public BoardDetachEvent handle(BoardDetachEvent event) {
        if(data.remove(event.target, BoardCardComponent.class) == null) {
            throw new IllegalStateException();
        }
        return event;
    }
    
}