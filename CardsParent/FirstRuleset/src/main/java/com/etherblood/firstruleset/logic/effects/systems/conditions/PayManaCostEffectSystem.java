package com.etherblood.firstruleset.logic.effects.systems.conditions;

import com.etherblood.cardsmatch.cardgame.AbstractMatchSystem;
import com.etherblood.cardsmatch.cardgame.match.Autowire;
import com.etherblood.firstruleset.logic.battle.stats.ManaCostComponent;
import com.etherblood.firstruleset.logic.effects.EffectTriggerEntityComponent;
import com.etherblood.firstruleset.logic.effects.effects.mana.PayManaCostEffectComponent;
import com.etherblood.firstruleset.logic.player.OwnerComponent;
import com.etherblood.firstruleset.logic.player.ManaComponent;
import com.etherblood.firstruleset.logic.effects.EffectEvent;
import com.etherblood.firstruleset.logic.mana.SetManaEvent;
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
