package com.etherblood.cardsmatch.cardgame.events.summon.systems;

import com.etherblood.cardsmatch.cardgame.AbstractMatchSystem;
import com.etherblood.match.Autowire;
import com.etherblood.cardsmatch.cardgame.components.effects.EffectTriggerEntityComponent;
import com.etherblood.cardsmatch.cardgame.components.effects.triggers.BattlecryTriggerComponent;
import com.etherblood.cardsmatch.cardgame.events.effects.TriggerEffectEvent;
import com.etherblood.cardsmatch.cardgame.events.summon.SummonEvent;
import com.etherblood.entitysystem.data.EntityComponentMapReadonly;
import com.etherblood.entitysystem.data.EntityId;
import com.etherblood.entitysystem.filters.AbstractComponentFieldValueFilter;
import com.etherblood.entitysystem.filters.EqualityOperator;
import com.etherblood.entitysystem.filters.FilterQuery;

/**
 *
 * @author Philipp
 */
public class BattlecrySystem extends AbstractMatchSystem<SummonEvent> {
    @Autowire
    private EntityComponentMapReadonly data;
    private final AbstractComponentFieldValueFilter<EffectTriggerEntityComponent> triggerFilter = EffectTriggerEntityComponent.createTriggerFilter(new EqualityOperator());
    private final FilterQuery battlecryTriggerQuery = new FilterQuery()
            .setBaseClass(BattlecryTriggerComponent.class)
            .addComponentFilter(triggerFilter);
    @Override
    public SummonEvent handle(SummonEvent event) {
        triggerFilter.setValue(event.minion);
        for (EntityId entity : battlecryTriggerQuery.list(data)) {//TODO: order results
            enqueueEvent(new TriggerEffectEvent(entity));
        }
        return event;
    }
}
