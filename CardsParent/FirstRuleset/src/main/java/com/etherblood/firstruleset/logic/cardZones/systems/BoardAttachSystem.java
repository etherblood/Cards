/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.etherblood.firstruleset.logic.cardZones.systems;

import com.etherblood.cardsmatch.cardgame.AbstractMatchSystem;
import com.etherblood.cardscontext.Autowire;
import com.etherblood.firstruleset.logic.cardZones.components.BoardCardComponent;
import com.etherblood.firstruleset.logic.cardZones.events.BoardAttachEvent;
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
