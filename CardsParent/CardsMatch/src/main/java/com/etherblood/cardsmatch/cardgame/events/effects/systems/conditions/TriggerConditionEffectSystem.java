package com.etherblood.cardsmatch.cardgame.events.effects.systems.conditions;

import com.etherblood.cardsmatch.cardgame.AbstractMatchSystem;
import com.etherblood.cardsmatch.cardgame.Autowire;
import com.etherblood.cardsmatch.cardgame.components.effects.EffectTriggerEntityComponent;
import com.etherblood.cardsmatch.cardgame.components.effects.conditions.TriggerConditionEffectComponent;
import com.etherblood.cardsmatch.cardgame.components.misc.OwnerComponent;
import com.etherblood.cardsmatch.cardgame.events.effects.EffectEvent;
import com.etherblood.entitysystem.data.EntityComponentMapReadonly;
import com.etherblood.entitysystem.data.EntityId;

/**
 *
 * @author Philipp
 */
public class TriggerConditionEffectSystem extends AbstractMatchSystem<EffectEvent> {
    @Autowire
    private EntityComponentMapReadonly data;

    @Override
    public EffectEvent handle(EffectEvent event) {
        TriggerConditionEffectComponent comp = data.get(event.effect, TriggerConditionEffectComponent.class);
        if(comp != null) {
            EntityId entity = data.get(event.effect, EffectTriggerEntityComponent.class).entity;
            if(!comp.condition.isFullfilled(data, entity, data.get(event.effect, OwnerComponent.class).player)) {
                return null;
            }
        }
        return event;
    }

}
