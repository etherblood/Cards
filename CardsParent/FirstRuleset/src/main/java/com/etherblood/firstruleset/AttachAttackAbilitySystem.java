/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.etherblood.firstruleset;

import com.etherblood.cardsmatch.cardgame.AbstractMatchSystem;
import com.etherblood.firstruleset.logic.battle.MinionComponent;
import com.etherblood.firstruleset.logic.player.OwnerComponent;
import com.etherblood.entitysystem.data.EntityComponentMapReadonly;
import com.etherblood.entitysystem.data.EntityIdFactory;
import com.etherblood.firstruleset.logic.cardZones.events.BoardAttachEvent;
import com.etherblood.firstruleset.logic.entities.AttachTemplateEvent;
import com.etherblood.cardscontext.Autowire;

/**
 *
 * @author Philipp
 */
public class AttachAttackAbilitySystem extends AbstractMatchSystem<BoardAttachEvent> {
    @Autowire
    private EntityComponentMapReadonly data;
    @Autowire
    protected EntityIdFactory idFactory;

    @Override
    public BoardAttachEvent handle(BoardAttachEvent event) {
        if(data.has(event.target, MinionComponent.class)) {
            enqueueEvent(new AttachTemplateEvent(idFactory.createEntity(), DefaultTemplateSetFactory.ACTIVATION_ATTACK, data.get(event.target, OwnerComponent.class).player, event.target));
        }
        return event;
    }
    
}