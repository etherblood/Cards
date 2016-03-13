package com.etherblood.firstruleset.logic.summon.systems;

import com.etherblood.cardsmatch.cardgame.AbstractMatchSystem;
import com.etherblood.cardsmatch.cardgame.match.Autowire;
import com.etherblood.firstruleset.logic.effects.EffectTriggerEntityComponent;
import com.etherblood.firstruleset.logic.effects.triggers.BattlecryTriggerComponent;
import com.etherblood.firstruleset.logic.summon.SummonEvent;
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
