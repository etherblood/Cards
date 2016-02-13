package com.etherblood.cardsmatch.cardgame.events.effects.systems;

import com.etherblood.cardsmatch.cardgame.AbstractMatchSystem;
import com.etherblood.cardsmatch.cardgame.Autowire;
import com.etherblood.cardsmatch.cardgame.components.effects.effects.DealDamageEffectComponent;
import com.etherblood.cardsmatch.cardgame.eventData.EffectTargets;
import com.etherblood.cardsmatch.cardgame.events.damage.DamageEvent;
import com.etherblood.cardsmatch.cardgame.events.effects.EffectEvent;
import com.etherblood.entitysystem.data.EntityComponentMapReadonly;
import com.etherblood.entitysystem.data.EntityId;

/**
 *
 * @author Philipp
 */
public class DealDamageEffectSystem extends AbstractMatchSystem<EffectEvent> {
    @Autowire
    private EntityComponentMapReadonly data;

    @Override
    public EffectEvent handle(EffectEvent event) {
        DealDamageEffectComponent damageComponent = data.get(event.effect, DealDamageEffectComponent.class);
        if(damageComponent != null && damageComponent.damage > 0) {
            for (EntityId target : eventData().get(EffectTargets.class).targets) {
//            for (EntityId target : data.get(event.effect, EffectTargetsComponent.class).targets) {
                enqueueEvent(new DamageEvent(event.effect, target, damageComponent.damage));
            }
        }
        return event;
    }

}
