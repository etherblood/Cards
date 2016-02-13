package com.etherblood.cardsmatch.cardgame.events.effects.systems.conditions;

import com.etherblood.cardsmatch.cardgame.AbstractMatchSystem;
import com.etherblood.cardsmatch.cardgame.Autowire;
import com.etherblood.cardsmatch.cardgame.components.effects.EffectTriggerEntityComponent;
import com.etherblood.cardsmatch.cardgame.components.effects.conditions.EffectPlayerTriggerConditionComponent;
import com.etherblood.cardsmatch.cardgame.components.effects.triggers.PlayerActivationTriggerComponent;
import com.etherblood.cardsmatch.cardgame.components.misc.OwnerComponent;
import com.etherblood.cardsmatch.cardgame.eventData.EffectTargets;
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
public class EffectPlayerTriggerConditionSystem extends AbstractMatchSystem<TriggerEffectEvent> {
    @Autowire
    private EntityComponentMapReadonly data;
    private final AbstractComponentFieldValueFilter<EffectTriggerEntityComponent> minionFilter = EffectTriggerEntityComponent.createTriggerFilter(new EqualityOperator());
    private final FilterQuery query = new FilterQuery()
            .setBaseClass(EffectPlayerTriggerConditionComponent.class)
            .addComponentFilter(minionFilter);
    @Override
    public TriggerEffectEvent handle(TriggerEffectEvent event) {
        if(data.has(event.effect, PlayerActivationTriggerComponent.class)) {
            EntityId minion = data.get(event.effect, EffectTriggerEntityComponent.class).entity;
            EntityId owner = data.get(minion, OwnerComponent.class).player;
            EffectTargets targetsComponent = eventData().get(EffectTargets.class);
            minionFilter.setValue(minion);
            for (EntityId conditionEntity : query.list(data)) {
                if(!data.get(conditionEntity, EffectPlayerTriggerConditionComponent.class).filter.pass(data, minion, owner, targetsComponent.targets)) {
                    return null;
                }
            }
        }
        return event;
    }

}
