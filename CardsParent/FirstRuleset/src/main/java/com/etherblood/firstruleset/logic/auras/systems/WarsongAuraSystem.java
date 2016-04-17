/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.etherblood.firstruleset.logic.auras.systems;

import com.etherblood.cardsmatch.cardgame.AbstractMatchSystem;
import com.etherblood.cardscontext.Autowire;
import com.etherblood.entitysystem.data.EntityComponentMap;
import com.etherblood.entitysystem.data.EntityId;
import com.etherblood.entitysystem.filters.AbstractComponentFieldValueFilter;
import com.etherblood.entitysystem.filters.EqualityOperator;
import com.etherblood.entitysystem.filters.FilterQuery;
import com.etherblood.firstruleset.logic.auras.WarsongAuraComponent;
import com.etherblood.firstruleset.logic.battle.buffs.ChargeComponent;
import com.etherblood.firstruleset.logic.battle.stats.AttackComponent;
import com.etherblood.firstruleset.logic.cardZones.components.BoardCardComponent;
import com.etherblood.firstruleset.logic.cardZones.events.BoardAttachEvent;
import com.etherblood.firstruleset.logic.player.OwnerComponent;

/**
 *
 * @author Philipp
 */
public class WarsongAuraSystem extends AbstractMatchSystem<BoardAttachEvent> {
    @Autowire
    private EntityComponentMap data;
    
    @Override
    public BoardAttachEvent handle(BoardAttachEvent event) {
        AttackComponent attack = data.get(event.target, AttackComponent.class);
        if(attack != null) {
            EntityId owner = data.get(event.target, OwnerComponent.class).player;
            AbstractComponentFieldValueFilter<OwnerComponent> ownerFilter = OwnerComponent.createPlayerFilter(new EqualityOperator());
            ownerFilter.setValue(owner);
            FilterQuery query = new FilterQuery().setBaseClass(WarsongAuraComponent.class).addComponentClassFilter(BoardCardComponent.class).addComponentFilter(ownerFilter);
            for (EntityId auraSource : query.list(data)) {
                if(!auraSource.equals(event.target) && attack.attack <= data.get(auraSource, WarsongAuraComponent.class).maxAttack) {
    //              enqueueEvent(new AttachChargeEvent(event.target));
                    data.set(event.target, new ChargeComponent());//TODO: fire event instead, see line above
                    break;
                }
            }
        }
        return event;
    }
    
}
