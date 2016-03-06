package com.etherblood.cardsmatch.cardgame.events.effects.systems.triggers;

import com.etherblood.cardsmatch.cardgame.AbstractMatchSystem;
import com.etherblood.cardsmatch.cardgame.ValidEffectTargetsSelector;
import com.etherblood.match.Autowire;
import com.etherblood.cardsmatch.cardgame.components.effects.targeting.EffectIsTargetedComponent;
import com.etherblood.cardsmatch.cardgame.components.effects.targeting.EffectRequiresUserTargetsComponent;
import com.etherblood.cardsmatch.cardgame.eventData.EffectTargets;
import com.etherblood.cardsmatch.cardgame.events.effects.TriggerEffectEvent;
import com.etherblood.entitysystem.data.EntityComponentMapReadonly;
import com.etherblood.entitysystem.data.EntityId;
import java.util.Arrays;
import java.util.List;

/**
 *
 * @author Philipp
 */
public class SelectTargetsEffectSystem extends AbstractMatchSystem<TriggerEffectEvent> {

    @Autowire
    private EntityComponentMapReadonly data;
    @Autowire
    private ValidEffectTargetsSelector selector;

    @Override
    public TriggerEffectEvent handle(TriggerEffectEvent event) {
        if (data.has(event.effect, EffectIsTargetedComponent.class)) {
            List<EntityId> list = selector.selectTargets(event.effect);
            if (data.has(event.effect, EffectRequiresUserTargetsComponent.class)) {
                EffectTargets userTargets = eventData().get(EffectTargets.class);
                if (!list.containsAll(Arrays.asList(userTargets.targets))) {
                    throw new IllegalStateException("selected user targets are not valid");
                }
            } else {
                EntityId[] targets = list.toArray(new EntityId[list.size()]);
                eventData().push(new EffectTargets(targets));
            }
        }
        return event;
    }
}
