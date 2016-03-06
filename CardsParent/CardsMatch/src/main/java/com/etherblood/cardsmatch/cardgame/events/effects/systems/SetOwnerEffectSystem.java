/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.etherblood.cardsmatch.cardgame.events.effects.systems;

import com.etherblood.cardsmatch.cardgame.AbstractMatchSystem;
import com.etherblood.match.Autowire;
import com.etherblood.cardsmatch.cardgame.components.effects.EffectTriggerEntityComponent;
import com.etherblood.cardsmatch.cardgame.components.effects.effects.MakeAllyEffectComponent;
import com.etherblood.cardsmatch.cardgame.components.misc.OwnerComponent;
import com.etherblood.cardsmatch.cardgame.eventData.EffectTargets;
import com.etherblood.cardsmatch.cardgame.events.effects.EffectEvent;
import com.etherblood.cardsmatch.cardgame.events.setOwner.SetOwnerEvent;
import com.etherblood.cardsmatch.cardgame.rng.RngFactory;
import com.etherblood.entitysystem.data.EntityComponentMapReadonly;
import com.etherblood.entitysystem.data.EntityId;

/**
 *
 * @author Philipp
 */
public class SetOwnerEffectSystem extends AbstractMatchSystem<EffectEvent> {

    @Autowire
    private EntityComponentMapReadonly data;

    @Override
    public EffectEvent handle(EffectEvent event) {
        EntityId owner;
        if (data.has(event.effect, MakeAllyEffectComponent.class)) {
            owner = data.get(event.effect, OwnerComponent.class).player;
        } else if (data.has(event.effect, MakeAllyEffectComponent.class)) {
            owner = data.get(event.effect, OwnerComponent.class).player;
        } else {
            return event;
        }
        for (EntityId target : eventData().get(EffectTargets.class).targets) {
            enqueueEvent(new SetOwnerEvent(target, owner));
        }
        return event;
    }
}
