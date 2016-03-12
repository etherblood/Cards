/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.etherblood.cardsmatch.cardgame.events.effects.systems;

import com.etherblood.cardsmatch.cardgame.AbstractMatchSystem;
import com.etherblood.match.Autowire;
import com.etherblood.cardsmatch.cardgame.components.effects.effects.cardzone.BoardAttachEffectComponent;
import com.etherblood.cardsmatch.cardgame.eventData.EffectTargets;
import com.etherblood.cardsmatch.cardgame.events.cardZones.BoardAttachEvent;
import com.etherblood.cardsmatch.cardgame.events.effects.EffectEvent;
import com.etherblood.entitysystem.data.EntityComponentMapReadonly;
import com.etherblood.entitysystem.data.EntityId;

/**
 *
 * @author Philipp
 */
public class BoardAttachEffectSystem extends AbstractMatchSystem<EffectEvent> {

    @Autowire
    private EntityComponentMapReadonly data;

    @Override
    public EffectEvent handle(EffectEvent event) {
        if (data.has(event.effect, BoardAttachEffectComponent.class)) {
            EffectTargets targets = eventData().get(EffectTargets.class);
            for (EntityId target : targets.targets) {
                enqueueEvent(new BoardAttachEvent(target));
            }
        }
        return event;
    }

}
