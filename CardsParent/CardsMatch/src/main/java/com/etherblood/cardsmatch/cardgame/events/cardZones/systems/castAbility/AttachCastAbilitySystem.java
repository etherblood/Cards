package com.etherblood.cardsmatch.cardgame.events.cardZones.systems.castAbility;

import com.etherblood.cardsmatch.cardgame.AbstractMatchSystem;
import com.etherblood.cardsmatch.cardgame.Autowire;
import com.etherblood.cardsmatch.cardgame.components.cards.CastTemplateComponent;
import com.etherblood.cardsmatch.cardgame.components.misc.OwnerComponent;
import com.etherblood.cardsmatch.cardgame.events.cardZones.HandAttachEvent;
import com.etherblood.cardsmatch.cardgame.events.entities.AttachTemplateEvent;
import com.etherblood.entitysystem.data.EntityComponentMapReadonly;
import com.etherblood.entitysystem.data.EntityId;
import com.etherblood.entitysystem.data.EntityIdFactory;

/**
 *
 * @author Philipp
 */
public class AttachCastAbilitySystem extends AbstractMatchSystem<HandAttachEvent> {
    @Autowire
    private EntityComponentMapReadonly data;
    @Autowire
    protected EntityIdFactory idFactory;

    @Override
    public HandAttachEvent handle(HandAttachEvent event) {
        CastTemplateComponent castTemplateComp = data.get(event.target, CastTemplateComponent.class);
        if(castTemplateComp != null) {
            EntityId entity = idFactory.createEntity();
            EntityId owner = data.get(event.target, OwnerComponent.class).player;
            enqueueEvent(new AttachTemplateEvent(entity, castTemplateComp.template, owner, event.target));
        }
        return event;
    }

}
