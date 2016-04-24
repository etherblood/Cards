package com.etherblood.firstruleset.logic.effects.systems;

import com.etherblood.cardsmatch.cardgame.AbstractMatchSystem;
import com.etherblood.cardscontext.Autowire;
import com.etherblood.firstruleset.logic.effects.effects.MakeAllyEffectComponent;
import com.etherblood.firstruleset.logic.effects.effects.MakeEnemyEffectComponent;
import com.etherblood.firstruleset.logic.player.OwnerComponent;
import com.etherblood.firstruleset.logic.player.NextTurnPlayerComponent;
import com.etherblood.firstruleset.logic.effects.EffectEvent;
import com.etherblood.firstruleset.logic.setOwner.SetOwnerEvent;
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
        } else if (data.has(event.effect, MakeEnemyEffectComponent.class)) {
            owner = data.get(data.get(event.effect, OwnerComponent.class).player, NextTurnPlayerComponent.class).player;
        } else {
            return event;
        }
        for (EntityId target : event.targets) {
            enqueueEvent(new SetOwnerEvent(target, owner));
        }
        return event;
    }
}
