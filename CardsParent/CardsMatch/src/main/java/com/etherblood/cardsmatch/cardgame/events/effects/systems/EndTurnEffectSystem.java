/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.etherblood.cardsmatch.cardgame.events.effects.systems;

import com.etherblood.cardsmatch.cardgame.AbstractMatchSystem;
import com.etherblood.cardsmatch.cardgame.Autowire;
import com.etherblood.cardsmatch.cardgame.components.effects.effects.EndTurnEffectComponent;
import com.etherblood.cardsmatch.cardgame.components.player.ItsMyTurnComponent;
import com.etherblood.cardsmatch.cardgame.components.player.PlayerComponent;
import com.etherblood.cardsmatch.cardgame.eventData.EffectTargets;
import com.etherblood.cardsmatch.cardgame.events.effects.EffectEvent;
import com.etherblood.cardsmatch.cardgame.events.endTurn.EndTurnEvent;
import com.etherblood.cardsmatch.cardgame.events.gameover.PlayerLostEvent;
import com.etherblood.entitysystem.data.EntityComponentMapReadonly;
import com.etherblood.entitysystem.data.EntityId;

/**
 *
 * @author Philipp
 */
public class EndTurnEffectSystem extends AbstractMatchSystem<EffectEvent> {
    @Autowire
    private EntityComponentMapReadonly data;

    @Override
    public EffectEvent handle(EffectEvent event) {
        if(data.has(event.effect, EndTurnEffectComponent.class)) {
            EntityId[] targets = eventData().get(EffectTargets.class).targets;
            if(targets.length != 1) {
                throw new IllegalStateException("cannot end turn of different targets-length than 1");
            }
            enqueueEvent(new EndTurnEvent(targets[0]));
        }
        return event;
    }
    
}
