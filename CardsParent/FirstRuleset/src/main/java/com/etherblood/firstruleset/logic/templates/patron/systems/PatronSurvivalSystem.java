package com.etherblood.firstruleset.logic.templates.patron.systems;

import com.etherblood.cardsmatch.cardgame.AbstractMatchSystem;
import com.etherblood.cardsmatch.cardgame.match.Autowire;
import com.etherblood.entitysystem.data.EntityComponentMapReadonly;
import com.etherblood.entitysystem.data.EntityId;
import com.etherblood.entitysystem.data.EntityIdFactory;
import com.etherblood.firstruleset.logic.cardZones.components.BoardCardComponent;
import com.etherblood.firstruleset.logic.cardZones.events.BoardAttachEvent;
import com.etherblood.firstruleset.logic.entities.AttachTemplateEvent;
import com.etherblood.firstruleset.logic.player.OwnerComponent;
import com.etherblood.firstruleset.logic.templates.patron.PatronAbilityComponent;
import com.etherblood.firstruleset.logic.templates.patron.PatronSurvivalCheckEvent;

/**
 *
 * @author Philipp
 */
public class PatronSurvivalSystem extends AbstractMatchSystem<PatronSurvivalCheckEvent> {
    @Autowire
    private EntityComponentMapReadonly data;
    @Autowire
    private EntityIdFactory factory;
    
    @Override
    public PatronSurvivalCheckEvent handle(PatronSurvivalCheckEvent event) {
        if(data.has(event.target, BoardCardComponent.class)) {
            EntityId spawn = factory.createEntity();
            enqueueEvent(new AttachTemplateEvent(spawn, data.get(event.target, PatronAbilityComponent.class).template, data.get(event.target, OwnerComponent.class).player, null));
            enqueueEvent(new BoardAttachEvent(spawn));
        }
        return event;
    }

}
