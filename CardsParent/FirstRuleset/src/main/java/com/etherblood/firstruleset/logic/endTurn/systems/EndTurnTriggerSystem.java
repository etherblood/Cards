package com.etherblood.firstruleset.logic.endTurn.systems;

import com.etherblood.cardsmatch.cardgame.AbstractMatchSystem;
import com.etherblood.cardsmatch.cardgame.match.Autowire;
import com.etherblood.firstruleset.logic.cardZones.components.BoardCardComponent;
import com.etherblood.firstruleset.logic.effects.EffectTriggerEntityComponent;
import com.etherblood.firstruleset.logic.effects.triggers.EndTurnTriggerComponent;
import com.etherblood.firstruleset.logic.endTurn.EndTurnEvent;
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
public class EndTurnTriggerSystem extends AbstractMatchSystem<EndTurnEvent> {
    @Autowire
    private EntityComponentMapReadonly data;
    private final AbstractComponentFieldValueFilter<EffectTriggerEntityComponent> triggerFilter = EffectTriggerEntityComponent.createTriggerFilter(new EqualityOperator());
    private final FilterQuery triggerQuery = new FilterQuery()
            .setBaseClass(EndTurnTriggerComponent.class);
    @Override
    public EndTurnEvent handle(EndTurnEvent event) {
        for (EntityId entity : triggerQuery.list(data)) {//TODO: order results
            if(data.has(data.get(entity, EffectTriggerEntityComponent.class).entity, BoardCardComponent.class)) {
                enqueueEvent(new TriggerEffectEvent(entity));
            }
        }
        return event;
    }
}
