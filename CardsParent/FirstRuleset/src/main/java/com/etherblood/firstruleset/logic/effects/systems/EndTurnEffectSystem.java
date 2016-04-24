package com.etherblood.firstruleset.logic.effects.systems;

import com.etherblood.cardsmatch.cardgame.AbstractMatchSystem;
import com.etherblood.cardscontext.Autowire;
import com.etherblood.firstruleset.logic.effects.effects.EndTurnEffectComponent;
import com.etherblood.firstruleset.logic.effects.EffectEvent;
import com.etherblood.firstruleset.logic.endTurn.EndTurnEvent;
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
            EntityId[] targets = event.targets;
            if(targets.length != 1) {
                throw new IllegalStateException("cannot end turn of different targets-length than 1, actual length is " + targets.length);
            }
            enqueueEvent(new EndTurnEvent(targets[0]));
        }
        return event;
    }
    
}
