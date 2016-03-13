/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.etherblood.firstruleset.logic.effects.systems.triggers;

import com.etherblood.cardsmatch.cardgame.AbstractMatchSystem;
import com.etherblood.cardsmatch.cardgame.match.Autowire;
import com.etherblood.firstruleset.logic.effects.targeting.CreateSingleTargetEntityEffectComponent;
import com.etherblood.firstruleset.eventData.EffectTargets;
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
            eventData().push(new EffectTargets(idFactory.createEntity()));
//            data.set(event.effect, new EffectTargetsComponent(entityFactory.createEntity()));
        }
        return event;
    }
    
}
