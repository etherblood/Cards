/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.etherblood.firstruleset.logic.cardZones.systems.attackAbility;

import com.etherblood.cardsmatch.cardgame.AbstractMatchSystem;
import com.etherblood.cardscontext.Autowire;
import com.etherblood.firstruleset.logic.cards.DeleteOnTriggerRemovedFromBoard;
import com.etherblood.firstruleset.logic.effects.EffectTriggerEntityComponent;
import com.etherblood.firstruleset.logic.cardZones.events.BoardDetachEvent;
import com.etherblood.firstruleset.logic.entities.DeleteEntityEvent;
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