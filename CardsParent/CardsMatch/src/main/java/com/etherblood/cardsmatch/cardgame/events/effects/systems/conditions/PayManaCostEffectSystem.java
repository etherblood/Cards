package com.etherblood.cardsmatch.cardgame.events.effects.systems.conditions;

import com.etherblood.cardsmatch.cardgame.AbstractMatchSystem;
import com.etherblood.cardsmatch.cardgame.Autowire;
import com.etherblood.cardsmatch.cardgame.components.battle.stats.ManaCostComponent;
import com.etherblood.cardsmatch.cardgame.components.effects.EffectTriggerEntityComponent;
import com.etherblood.cardsmatch.cardgame.components.effects.effects.mana.PayManaCostEffectComponent;
import com.etherblood.cardsmatch.cardgame.components.misc.OwnerComponent;
import com.etherblood.cardsmatch.cardgame.components.player.ManaComponent;
import com.etherblood.cardsmatch.cardgame.events.effects.EffectEvent;
import com.etherblood.cardsmatch.cardgame.events.mana.SetManaEvent;
import com.etherblood.entitysystem.data.EntityComponentMapReadonly;
import com.etherblood.entitysystem.data.EntityId;

/**
 *
 * @author Philipp
 */
public class PayManaCostEffectSystem extends AbstractMatchSystem<EffectEvent> {
    @Autowire
    private EntityComponentMapReadonly data;
    @Override
    public EffectEvent handle(EffectEvent event) {
        if(data.has(event.effect, PayManaCostEffectComponent.class)) {
            EntityId trigger = data.get(event.effect, EffectTriggerEntityComponent.class).entity;
            EntityId owner = data.get(event.effect, OwnerComponent.class).player;
            
            ManaCostComponent costComponent = data.get(trigger, ManaCostComponent.class);
            int cost = costComponent == null? 0: costComponent.mana;
            ManaComponent manaComponent = data.get(owner, ManaComponent.class);
            int mana = manaComponent == null? 0: manaComponent.mana;
            if(cost <= mana) {
                mana -= cost;
                enqueueEvent(new SetManaEvent(owner, mana));
            } else {
                return null;
            }
        }
        return event;
    }
}
