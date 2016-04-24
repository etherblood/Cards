package com.etherblood.firstruleset.logic.effects.systems.triggers;

import com.etherblood.cardsmatch.cardgame.AbstractMatchSystem;
import com.etherblood.cardscontext.Autowire;
import com.etherblood.firstruleset.logic.effects.targeting.CreateSingleTargetEntityEffectComponent;
import com.etherblood.firstruleset.logic.effects.TriggerEffectEvent;
import com.etherblood.entitysystem.data.EntityComponentMapReadonly;
import com.etherblood.entitysystem.data.EntityIdFactory;

/**
 *
 * @author Philipp
 */
public class CreateSingleTargetEntityEffectSystem extends AbstractMatchSystem<TriggerEffectEvent> {
    @Autowire
    private EntityComponentMapReadonly data;
    @Autowire
    protected EntityIdFactory idFactory;

    @Override
    public TriggerEffectEvent handle(TriggerEffectEvent event) {
        if(data.has(event.effect, CreateSingleTargetEntityEffectComponent.class)) {
            event = new TriggerEffectEvent(event.effect, idFactory.createEntity());
        }
        return event;
    }
    
}
