package com.etherblood.firstruleset.logic.effects.systems.triggers;

import com.etherblood.cardsmatch.cardgame.EntityUtils;
import com.etherblood.cardsmatch.cardgame.AbstractMatchSystem;
import com.etherblood.cardsmatch.cardgame.ValidEffectTargetsSelector;
import com.etherblood.firstruleset.logic.effects.EffectTriggerEntityComponent;
import com.etherblood.cardscontext.Autowire;
import com.etherblood.cardsmatchapi.IllegalCommandException;
import com.etherblood.firstruleset.logic.effects.targeting.EffectIsTargetedComponent;
import com.etherblood.firstruleset.logic.effects.targeting.EffectMinimumTargetsRequiredComponent;
import com.etherblood.firstruleset.logic.effects.targeting.EffectRequiresUserTargetsComponent;
import com.etherblood.cardsmatch.cardgame.components.misc.NameComponent;
import com.etherblood.cardsmatch.cardgame.rng.RngFactory;
import com.etherblood.firstruleset.eventData.EffectTargets;
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
                EntityId[] targets = eventData().get(EffectTargets.class).targets;
                if(data.has(event.effect, EffectTargetsSingleRandomComponent.class)) {
                    logEffect(event, list, targets);
                    throw new IllegalStateException("user targeted events with rng targets not supported");
                }
                if (!list.containsAll(Arrays.asList(targets))) {
                    logEffect(event, list, targets);
                    throw new IllegalCommandException("selected user targets are not valid");
                }
            } else {
                if (list.size() > 1 && data.has(event.effect, EffectTargetsSingleRandomComponent.class)) {
                    EntityId randomItem = list.get(rng.nextInt(list.size()));
                    list.clear();
                    list.add(randomItem);
                }
                EntityId[] targets = list.toArray(new EntityId[list.size()]);
                eventData().push(new EffectTargets(targets));
            }
            EffectMinimumTargetsRequiredComponent minimumTargetsComponent = data.get(event.effect, EffectMinimumTargetsRequiredComponent.class);
            if(minimumTargetsComponent != null) {
                EntityId[] targets = eventData().get(EffectTargets.class).targets;
                int actualTargets = targets.length;
                if(actualTargets < minimumTargetsComponent.count) {
                    logEffect(event, list, targets);
                    throw new IllegalCommandException(data.get(event.effect, NameComponent.class).name + " of " + data.get(data.get(event.effect, EffectTriggerEntityComponent.class).entity, NameComponent.class).name + " requires " + minimumTargetsComponent.count + " targets, but only " + actualTargets + " were available");
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
