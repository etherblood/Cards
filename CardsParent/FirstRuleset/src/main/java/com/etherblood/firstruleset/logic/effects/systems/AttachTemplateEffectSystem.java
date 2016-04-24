package com.etherblood.firstruleset.logic.effects.systems;

import com.etherblood.cardsmatch.cardgame.AbstractMatchSystem;
import com.etherblood.cardscontext.Autowire;
import com.etherblood.firstruleset.logic.effects.effects.AttachTemplateEffectComponent;
import com.etherblood.firstruleset.logic.player.OwnerComponent;
import com.etherblood.firstruleset.logic.effects.EffectEvent;
import com.etherblood.firstruleset.logic.entities.AttachTemplateEvent;
import com.etherblood.entitysystem.data.EntityComponentMapReadonly;
import com.etherblood.entitysystem.data.EntityId;

/**
 *
 * @author Philipp
 */
public class AttachTemplateEffectSystem extends AbstractMatchSystem<EffectEvent> {
    @Autowire
    private EntityComponentMapReadonly data;

    @Override
    public EffectEvent handle(EffectEvent event) {
        AttachTemplateEffectComponent attachTemplateComponent = data.get(event.effect, AttachTemplateEffectComponent.class);
        if(attachTemplateComponent != null) {
            for (EntityId target : event.targets) {
//            for (EntityId target : data.get(event.effect, EffectTargetsComponent.class).targets) {
                enqueueEvent(new AttachTemplateEvent(target, attachTemplateComponent.template, data.get(event.effect, OwnerComponent.class).player, null));
            }
        }
        return event;
    }
    
}
