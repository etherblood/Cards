/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.etherblood.firstruleset.logic.effects.systems;

import com.etherblood.cardsmatch.cardgame.AbstractMatchSystem;
import com.etherblood.cardscontext.Autowire;
import com.etherblood.firstruleset.logic.effects.effects.cardzone.BoardAttachEffectComponent;
import com.etherblood.firstruleset.eventData.EffectTargets;
import com.etherblood.firstruleset.logic.cardZones.events.BoardAttachEvent;
import com.etherblood.firstruleset.logic.effects.EffectEvent;
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
