package com.etherblood.firstruleset.logic.death.systems;

import com.etherblood.cardsmatch.cardgame.AbstractMatchSystem;
import com.etherblood.cardscontext.Autowire;
import com.etherblood.firstruleset.logic.effects.EffectTriggerEntityComponent;
import com.etherblood.firstruleset.logic.effects.triggers.DeathrattleTriggerComponent;
import com.etherblood.firstruleset.logic.death.DeathEvent;
import com.etherblood.entitysystem.data.EntityComponentMapReadonly;
import com.etherblood.entitysystem.data.EntityId;
import com.etherblood.entitysystem.filters.AbstractComponentFieldValueFilter;
import com.etherblood.entitysystem.filters.EqualityOperator;
import com.etherblood.entitysystem.filters.FilterQuery;
import com.etherblood.firstruleset.logic.effects.TriggerEffectEvent;

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
