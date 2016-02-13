package com.etherblood.cardsmatch.cardgame.events.effects.systems;

import com.etherblood.cardsmatch.cardgame.AbstractMatchSystem;
import com.etherblood.cardsmatch.cardgame.Autowire;
import com.etherblood.cardsmatch.cardgame.components.effects.effects.DrawEffectComponent;
import com.etherblood.cardsmatch.cardgame.eventData.EffectTargets;
import com.etherblood.cardsmatch.cardgame.events.draw.RequestDrawEvent;
import com.etherblood.cardsmatch.cardgame.events.effects.EffectEvent;
import com.etherblood.entitysystem.data.EntityComponentMapReadonly;
import com.etherblood.entitysystem.data.EntityId;

/**
 *
 * @author Philipp
 */
public class DrawEffectSystem extends AbstractMatchSystem<EffectEvent> {
    @Autowire
    private EntityComponentMapReadonly data;

    @Override
    public EffectEvent handle(EffectEvent event) {
        DrawEffectComponent drawEffectComponent = data.get(event.effect, DrawEffectComponent.class);
        if (drawEffectComponent != null) {
            for (int i = 0; i < drawEffectComponent.numCards; i++) {
            for (EntityId target : eventData().get(EffectTargets.class).targets) {
//                for (EntityId target : data.get(event.effect, EffectTargetsComponent.class).targets) {
                    enqueueEvent(new RequestDrawEvent(target));
                }
            }
        }
        return event;
    }
}
