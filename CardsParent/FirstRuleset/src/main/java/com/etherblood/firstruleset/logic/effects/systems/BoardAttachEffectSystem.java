package com.etherblood.firstruleset.logic.effects.systems;

import com.etherblood.cardsmatch.cardgame.AbstractMatchSystem;
import com.etherblood.cardscontext.Autowire;
import com.etherblood.firstruleset.logic.effects.effects.cardzone.BoardAttachEffectComponent;
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
            for (EntityId target : event.targets) {
                enqueueEvent(new BoardAttachEvent(target));
            }
        }
        return event;
    }

}
