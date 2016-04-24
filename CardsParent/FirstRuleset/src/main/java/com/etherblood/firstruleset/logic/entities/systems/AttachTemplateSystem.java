/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.etherblood.firstruleset.logic.entities.systems;

import com.etherblood.cardsmatch.cardgame.EntityTemplate;
import com.etherblood.cardsmatch.cardgame.AbstractMatchSystem;
import com.etherblood.cardscontext.Autowire;
import com.etherblood.cardsmatch.cardgame.TemplateSet;
import com.etherblood.firstruleset.logic.effects.EffectTriggerEntityComponent;
import com.etherblood.firstruleset.logic.entities.AttachTemplateEvent;
import com.etherblood.firstruleset.logic.setOwner.SetOwnerEvent;
import com.etherblood.entitysystem.data.EntityComponent;
import com.etherblood.entitysystem.data.EntityComponentMap;
import com.etherblood.entitysystem.data.EntityId;
import com.etherblood.entitysystem.data.EntityIdFactory;

/**
 *
 * @author Philipp
 */
public class AttachTemplateSystem extends AbstractMatchSystem<AttachTemplateEvent> {
    @Autowire
    private EntityComponentMap data;
    @Autowire
    protected EntityIdFactory idFactory;
    @Autowire
    public TemplateSet templates;

    @Override
    public AttachTemplateEvent handle(AttachTemplateEvent event) {
        attachTemplate(templates.getTemplate(event.template), event);
        return event;
    }

    private void attachTemplate(EntityTemplate template, AttachTemplateEvent event) {
        for (EntityComponent component : template.getComponents()) {
            data.set(event.target, component);
        }
        if(event.owner != null) {
            events.fireEvent(new SetOwnerEvent(event.target, event.owner));
        }
        if(event.parent != null) {//template instanceof TriggerEntityTemplate) {
            if(template.isCollectible()) {
                throw new IllegalStateException();//remove if intended
            }
            data.set(event.target, new EffectTriggerEntityComponent(event.parent));
        }
        for (String childName : template.getChildTemplates()) {
            EntityId child = idFactory.createEntity();
            events.fireEvent(new AttachTemplateEvent(child, childName, event.owner, event.target));
        }
    }
    
}
