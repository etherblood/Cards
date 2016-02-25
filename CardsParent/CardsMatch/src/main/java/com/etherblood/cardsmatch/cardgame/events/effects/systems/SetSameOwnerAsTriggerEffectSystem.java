/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.etherblood.cardsmatch.cardgame.events.effects.systems;

import com.etherblood.cardsmatch.cardgame.AbstractMatchSystem;
import com.etherblood.cardsmatch.cardgame.Autowire;
import com.etherblood.cardsmatch.cardgame.components.effects.EffectTriggerEntityComponent;
import com.etherblood.cardsmatch.cardgame.components.effects.effects.SetOwnerEffectComponent;
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
public class SetSameOwnerAsTriggerEffectSystem extends AbstractMatchSystem<EffectEvent> {
    @Autowire
    private EntityComponentMapReadonly data;
    @Autowire
    private RngFactory rng;

    @Override
    public EffectEvent handle(EffectEvent event) {
        SetOwnerEffectComponent ownerFilterComponent = data.get(event.effect, SetOwnerEffectComponent.class);
        if(ownerFilterComponent != null) {
            EntityId trigger = data.get(event.effect, EffectTriggerEntityComponent.class).entity;
            EntityId owner = ownerFilterComponent.filter.select(data, event.effect, data.get(trigger, OwnerComponent.class).player, rng).get(0);//data.get(trigger, OwnerComponent.class).player;
            for (EntityId target : eventData().get(EffectTargets.class).targets) {
//            for (EntityId target : data.get(event.effect, EffectTargetsComponent.class).targets) {
                enqueueEvent(new SetOwnerEvent(target, owner));
            }
        }
        return event;
    }
    
}
