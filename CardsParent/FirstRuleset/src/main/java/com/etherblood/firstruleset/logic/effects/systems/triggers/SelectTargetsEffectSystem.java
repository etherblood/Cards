package com.etherblood.firstruleset.logic.effects.systems.triggers;

import com.etherblood.cardsmatch.cardgame.EntityUtils;
import com.etherblood.cardsmatch.cardgame.AbstractMatchSystem;
import com.etherblood.cardsmatch.cardgame.ValidEffectTargetsSelector;
import com.etherblood.firstruleset.logic.effects.EffectTriggerEntityComponent;
import com.etherblood.cardscontext.Autowire;
import com.etherblood.firstruleset.logic.effects.targeting.EffectIsTargetedComponent;
import com.etherblood.firstruleset.logic.effects.targeting.EffectMinimumTargetsRequiredComponent;
import com.etherblood.firstruleset.logic.effects.targeting.EffectRequiresUserTargetsComponent;
import com.etherblood.cardsmatch.cardgame.components.misc.NameComponent;
import com.etherblood.cardsmatch.cardgame.rng.RngFactory;
import com.etherblood.firstruleset.logic.effects.TriggerEffectEvent;
import com.etherblood.entitysystem.data.EntityComponentMapReadonly;
import com.etherblood.entitysystem.data.EntityId;
import com.etherblood.firstruleset.logic.effects.targeting.EffectTargetsSingleRandomComponent;
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
    @Autowire
    private RngFactory rng;

    @Override
    public TriggerEffectEvent handle(TriggerEffectEvent event) {
        if (data.has(event.effect, EffectIsTargetedComponent.class)) {
            List<EntityId> list = selector.selectTargets(event.effect);
            if (data.has(event.effect, EffectRequiresUserTargetsComponent.class)) {
                if(data.has(event.effect, EffectTargetsSingleRandomComponent.class)) {
                    logEffect(event, list, event.targets);
                    throw new IllegalStateException("user targeted events with rng targets not supported");
                }
                if (!list.containsAll(Arrays.asList(event.targets))) {
                    logEffect(event, list, event.targets);
                    throw new IllegalStateException("selected user targets are not valid");
                }
            } else {
                if (list.size() > 1 && data.has(event.effect, EffectTargetsSingleRandomComponent.class)) {
                    EntityId randomItem = list.get(rng.nextInt(list.size()));
                    list.clear();
                    list.add(randomItem);
                }
                EntityId[] targets = list.toArray(new EntityId[list.size()]);
                event = new TriggerEffectEvent(event.effect, targets);
            }
            EffectMinimumTargetsRequiredComponent minimumTargetsComponent = data.get(event.effect, EffectMinimumTargetsRequiredComponent.class);
            if(minimumTargetsComponent != null) {
                int actualTargets = event.targets.length;
                if(actualTargets < minimumTargetsComponent.count) {
                    logEffect(event, list, event.targets);
                    throw new IllegalStateException(data.get(event.effect, NameComponent.class).name + " of " + data.get(data.get(event.effect, EffectTriggerEntityComponent.class).entity, NameComponent.class).name + " requires " + minimumTargetsComponent.count + " targets, but only " + actualTargets + " were available");
                }
            }
        }
        return event;
    }

    private void logEffect(TriggerEffectEvent event, List<EntityId> list, EntityId[] targets) {
        System.out.println("effect is " + EntityUtils.toString(data, event.effect) + " of " + EntityUtils.toString(data, data.get(event.effect, EffectTriggerEntityComponent.class).entity));
        System.out.println("selected: " + EntityUtils.toString(data, targets));
        System.out.println("expected any of: " + EntityUtils.toString(data, list));
    }
}
