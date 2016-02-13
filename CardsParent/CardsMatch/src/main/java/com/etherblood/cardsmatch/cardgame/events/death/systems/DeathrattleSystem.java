/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.etherblood.cardsmatch.cardgame.events.death.systems;

import com.etherblood.cardsmatch.cardgame.AbstractMatchSystem;
import com.etherblood.cardsmatch.cardgame.Autowire;
import com.etherblood.cardsmatch.cardgame.components.effects.EffectTriggerEntityComponent;
import com.etherblood.cardsmatch.cardgame.components.effects.triggers.DeathrattleTriggerComponent;
import com.etherblood.cardsmatch.cardgame.events.death.DeathEvent;
import com.etherblood.cardsmatch.cardgame.events.effects.TriggerEffectEvent;
import com.etherblood.entitysystem.data.EntityComponentMapReadonly;
import com.etherblood.entitysystem.data.EntityId;
import com.etherblood.entitysystem.filters.AbstractComponentFieldValueFilter;
import com.etherblood.entitysystem.filters.EqualityOperator;
import com.etherblood.entitysystem.filters.FilterQuery;

/**
 *
 * @author Philipp
 */
public class DeathrattleSystem extends AbstractMatchSystem<DeathEvent> {
    @Autowire
    private EntityComponentMapReadonly data;
    private final AbstractComponentFieldValueFilter<EffectTriggerEntityComponent> triggerFilter = EffectTriggerEntityComponent.createTriggerFilter(new EqualityOperator());
    private final FilterQuery deathrattleTriggerQuery = new FilterQuery()
            .setBaseClass(DeathrattleTriggerComponent.class)
            .addComponentFilter(triggerFilter);
    
    @Override
    public DeathEvent handle(DeathEvent event) {
        triggerFilter.setValue(event.entity);
        for (EntityId entity : deathrattleTriggerQuery.list(data)) {//TODO: order results
            enqueueEvent(new TriggerEffectEvent(entity));
        }
        return event;
    }
}
