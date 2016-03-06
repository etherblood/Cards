/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.etherblood.cardsmatch.cardgame.events.cardZones.systems.attackAbility;

import com.etherblood.cardsmatch.cardgame.DefaultTemplateSetFactory;
import com.etherblood.cardsmatch.cardgame.AbstractMatchSystem;
import com.etherblood.match.Autowire;
import com.etherblood.cardsmatch.cardgame.components.battle.MinionComponent;
import com.etherblood.cardsmatch.cardgame.components.misc.OwnerComponent;
import com.etherblood.cardsmatch.cardgame.events.cardZones.BoardAttachEvent;
import com.etherblood.cardsmatch.cardgame.events.entities.AttachTemplateEvent;
import com.etherblood.entitysystem.data.EntityComponentMapReadonly;
import com.etherblood.entitysystem.data.EntityIdFactory;

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