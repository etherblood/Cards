/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.etherblood.cardsmatch.cardgame.events.cardZones.systems.attackAbility;

import com.etherblood.cardsmatch.cardgame.AbstractMatchSystem;
import com.etherblood.cardsmatch.cardgame.Autowire;
import com.etherblood.cardsmatch.cardgame.components.cards.DeleteOnTriggerRemovedFromBoard;
import com.etherblood.cardsmatch.cardgame.components.effects.EffectTriggerEntityComponent;
import com.etherblood.cardsmatch.cardgame.events.cardZones.BoardDetachEvent;
import com.etherblood.cardsmatch.cardgame.events.entities.DeleteEntityEvent;
import com.etherblood.entitysystem.data.EntityComponentMapReadonly;
import com.etherblood.entitysystem.data.EntityId;
import com.etherblood.entitysystem.filters.AbstractComponentFieldValueFilter;
import com.etherblood.entitysystem.filters.EqualityOperator;
import com.etherblood.entitysystem.filters.FilterQuery;

/**
 *
 * @author Philipp
 */
public class DetachAttackAbilitySystem extends AbstractMatchSystem<BoardDetachEvent> {
    @Autowire
    private EntityComponentMapReadonly data;
    AbstractComponentFieldValueFilter<EffectTriggerEntityComponent> filter = EffectTriggerEntityComponent.createTriggerFilter(new EqualityOperator());
    FilterQuery query = new FilterQuery()
            .setBaseClass(DeleteOnTriggerRemovedFromBoard.class)
            .addComponentFilter(filter);

    @Override
    public BoardDetachEvent handle(BoardDetachEvent event) {
        filter.setValue(event.target);
        for (EntityId entity : query.list(data)) {
            enqueueEvent(new DeleteEntityEvent(entity));
        }
        return event;
    }
}