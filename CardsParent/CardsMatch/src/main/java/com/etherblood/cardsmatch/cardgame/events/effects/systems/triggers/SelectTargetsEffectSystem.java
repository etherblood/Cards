package com.etherblood.cardsmatch.cardgame.events.effects.systems.triggers;

import com.etherblood.EntityUtils;
import com.etherblood.cardsmatch.cardgame.AbstractMatchSystem;
import com.etherblood.cardsmatch.cardgame.ValidEffectTargetsSelector;
import com.etherblood.cardsmatch.cardgame.components.effects.EffectTriggerEntityComponent;
import com.etherblood.match.Autowire;
import com.etherblood.cardsmatch.cardgame.components.effects.targeting.EffectIsTargetedComponent;
import com.etherblood.cardsmatch.cardgame.components.effects.targeting.EffectMinimumTargetsRequiredComponent;
import com.etherblood.cardsmatch.cardgame.components.effects.targeting.EffectRequiresUserTargetsComponent;
import com.etherblood.cardsmatch.cardgame.components.misc.NameComponent;
import com.etherblood.cardsmatch.cardgame.components.misc.OwnerComponent;
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
                    System.out.println("selected: " + EntityUtils.toString(data, userTargets.targets));
                    System.out.println("expected any of: " + EntityUtils.toString(data, list));
                    throw new IllegalStateException("selected user targets are not valid");
                }
            } else {
                EntityId[] targets = list.toArray(new EntityId[list.size()]);
                eventData().push(new EffectTargets(targets));
            }
            EffectMinimumTargetsRequiredComponent minimumTargetsComponent = data.get(event.effect, EffectMinimumTargetsRequiredComponent.class);
            if(minimumTargetsComponent != null) {
                int actualTargets = eventData().get(EffectTargets.class).targets.length;
                if(actualTargets < minimumTargetsComponent.count) {
                    throw new IllegalStateException(data.get(event.effect, NameComponent.class).name + " of " + data.get(data.get(event.effect, EffectTriggerEntityComponent.class).entity, NameComponent.class).name + " requires " + minimumTargetsComponent.count + " targets, but only " + actualTargets + " were available");
                }
            }
        }
        return event;
    }
}
