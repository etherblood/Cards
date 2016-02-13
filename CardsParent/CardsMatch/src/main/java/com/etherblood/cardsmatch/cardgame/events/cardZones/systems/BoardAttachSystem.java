/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.etherblood.cardsmatch.cardgame.events.cardZones.systems;

import com.etherblood.cardsmatch.cardgame.AbstractMatchSystem;
import com.etherblood.cardsmatch.cardgame.Autowire;
import com.etherblood.cardsmatch.cardgame.components.cards.cardZone.BoardCardComponent;
import com.etherblood.cardsmatch.cardgame.events.cardZones.BoardAttachEvent;
import com.etherblood.entitysystem.data.EntityComponentMap;

/**
 *
 * @author Philipp
 */
public class BoardAttachSystem extends AbstractMatchSystem<BoardAttachEvent> {
    @Autowire
    private EntityComponentMap data;

    @Override
    public BoardAttachEvent handle(BoardAttachEvent event) {
        data.set(event.target, new BoardCardComponent());
        return event;
    }
    
}
