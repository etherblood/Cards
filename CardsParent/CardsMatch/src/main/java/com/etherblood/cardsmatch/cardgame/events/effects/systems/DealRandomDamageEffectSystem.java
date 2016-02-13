package com.etherblood.cardsmatch.cardgame.events.effects.systems;

import com.etherblood.cardsmatch.cardgame.AbstractMatchSystem;
import com.etherblood.cardsmatch.cardgame.Autowire;
import com.etherblood.cardsmatch.cardgame.RngFactory;
import com.etherblood.cardsmatch.cardgame.components.effects.effects.DealRandomDamageEffectComponent;
import com.etherblood.cardsmatch.cardgame.eventData.EffectTargets;
import com.etherblood.cardsmatch.cardgame.events.damage.DamageEvent;
import com.etherblood.cardsmatch.cardgame.events.effects.EffectEvent;
import com.etherblood.entitysystem.data.EntityComponentMapReadonly;
import com.etherblood.entitysystem.data.EntityId;

/**
 *
 * @author Philipp
 */
public class DealRandomDamageEffectSystem extends AbstractMatchSystem<EffectEvent> {
    @Autowire
    private EntityComponentMapReadonly data;
    @Autowire
    public RngFactory rng;

    @Override
    public EffectEvent handle(EffectEvent event) {
        DealRandomDamageEffectComponent damageComponent = data.get(event.effect, DealRandomDamageEffectComponent.class);
        if (damageComponent != null) {
            for (EntityId target : eventData().get(EffectTargets.class).targets) {
//            for (EntityId target : data.get(event.effect, EffectTargetsComponent.class).targets) {
                int damage = damageComponent.offset + rng.nextInt(damageComponent.rngRange);
                if (damage > 0) {
                    enqueueEvent(new DamageEvent(event.effect, target, damage));
                }
            }
        }
        return event;
    }

}
