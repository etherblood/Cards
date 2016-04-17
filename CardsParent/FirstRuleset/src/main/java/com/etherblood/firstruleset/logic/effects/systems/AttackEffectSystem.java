package com.etherblood.firstruleset.logic.effects.systems;

import com.etherblood.cardsmatch.cardgame.AbstractMatchSystem;
import com.etherblood.cardscontext.Autowire;
import com.etherblood.firstruleset.logic.effects.EffectTriggerEntityComponent;
import com.etherblood.firstruleset.logic.effects.effects.AttackEffectComponent;
import com.etherblood.firstruleset.eventData.EffectTargets;
import com.etherblood.firstruleset.logic.attack.AttackEvent;
import com.etherblood.firstruleset.logic.effects.EffectEvent;
import com.etherblood.entitysystem.data.EntityComponentMapReadonly;
import com.etherblood.entitysystem.data.EntityId;

/**
 *
 * @author Philipp
 */
public class AttackEffectSystem extends AbstractMatchSystem<EffectEvent> {
    @Autowire
    private EntityComponentMapReadonly data;

    @Override
    public EffectEvent handle(EffectEvent event) {
        if(data.has(event.effect, AttackEffectComponent.class)) {
            for (EntityId target : eventData().get(EffectTargets.class).targets) {
//            for (EntityId target : data.get(event.effect, EffectTargetsComponent.class).targets) {
                enqueueEvent(new AttackEvent(data.get(event.effect, EffectTriggerEntityComponent.class).entity, target));
            }
        }
        return event;
    }

}
