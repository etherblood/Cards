package com.etherblood.firstruleset.logic.entities;

import com.etherblood.entitysystem.data.EntityId;
import com.etherblood.eventsystem.GameEvent;

/**
 *
 * @author Philipp
 */
public class AttachTemplateEvent implements GameEvent {
    public final EntityId target, owner, parent;
    public final String template;

//    public AttachTemplateEvent(EntityId target, String template) {
//        this(target, template, null, null);
//    }

    public AttachTemplateEvent(EntityId target, String template, EntityId owner, EntityId parent) {
        this.target = target;
        this.template = template;
        this.owner = owner;
        this.parent = parent;
    }

    @Override
    public String toString() {
        return "AttachTemplateEvent{" + "target=" + target + ", owner=" + owner + ", parent=" + parent + ", template=" + template + '}';
    }
}
