package com.etherblood.firstruleset.logic.cardZones.systems.castAbility;

import com.etherblood.cardsmatch.cardgame.AbstractMatchSystem;
import com.etherblood.cardsmatch.cardgame.match.Autowire;
import com.etherblood.firstruleset.logic.cards.CastTemplateComponent;
import com.etherblood.firstruleset.logic.player.OwnerComponent;
import com.etherblood.firstruleset.logic.cardZones.events.HandAttachEvent;
import com.etherblood.firstruleset.logic.entities.AttachTemplateEvent;
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
