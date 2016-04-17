package com.etherblood.firstruleset.logic.effects.systems;

import com.etherblood.cardsmatch.cardgame.AbstractMatchSystem;
import com.etherblood.cardscontext.Autowire;
import com.etherblood.cardsmatch.cardgame.rng.RngFactory;
import com.etherblood.firstruleset.logic.effects.effects.DealRandomDamageEffectComponent;
import com.etherblood.firstruleset.eventData.EffectTargets;
import com.etherblood.firstruleset.logic.damage.DamageEvent;
import com.etherblood.firstruleset.logic.effects.EffectEvent;
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
                int damage = damageComponent.offset + rng.nextInt(damageComponent.rngRange);
                if (damage > 0) {
                    enqueueEvent(new DamageEvent(event.effect, target, damage));
                }
            }
        }
        return event;
    }

}
