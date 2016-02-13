/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.etherblood.cardsmatch.cardgame.events.effects.systems;

import com.etherblood.cardsmatch.cardgame.AbstractMatchSystem;
import com.etherblood.cardsmatch.cardgame.Autowire;
import com.etherblood.cardsmatch.cardgame.components.effects.effects.PlayerLostEffectComponent;
import com.etherblood.cardsmatch.cardgame.components.misc.OwnerComponent;
import com.etherblood.cardsmatch.cardgame.events.effects.EffectEvent;
import com.etherblood.cardsmatch.cardgame.events.gameover.PlayerLostEvent;
import com.etherblood.entitysystem.data.EntityComponentMapReadonly;

/**
 *
 * @author Philipp
 */
public class PlayerLostEffectSystem extends AbstractMatchSystem<EffectEvent> {
    @Autowire
    private EntityComponentMapReadonly data;

    @Override
    public EffectEvent handle(EffectEvent event) {
        if(data.has(event.effect, PlayerLostEffectComponent.class)) {
            enqueueEvent(new PlayerLostEvent(data.get(event.effect, OwnerComponent.class).player));
        }
        return event;
    }
    
}
