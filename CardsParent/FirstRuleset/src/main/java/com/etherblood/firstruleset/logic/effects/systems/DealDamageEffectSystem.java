package com.etherblood.firstruleset.logic.effects.systems;

import com.etherblood.cardsmatch.cardgame.AbstractMatchSystem;
import com.etherblood.cardscontext.Autowire;
import com.etherblood.firstruleset.logic.effects.effects.DealDamageEffectComponent;
import com.etherblood.firstruleset.logic.damage.DamageEvent;
import com.etherblood.firstruleset.logic.effects.EffectEvent;
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
            for (EntityId target : event.targets) {
//            for (EntityId target : data.get(event.effect, EffectTargetsComponent.class).targets) {
                enqueueEvent(new DamageEvent(event.effect, target, damageComponent.damage));
            }
        }
        return event;
    }

}
