/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.etherblood.cardsmatch.cardgame.events.cardZones.systems;

import com.etherblood.cardsmatch.cardgame.AbstractMatchSystem;
import com.etherblood.match.Autowire;
import com.etherblood.cardsmatch.cardgame.components.cards.cardZone.BoardCardComponent;
import com.etherblood.cardsmatch.cardgame.events.cardZones.BoardDetachEvent;
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