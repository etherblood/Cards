/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.etherblood.firstruleset.logic.death.systems;

import com.etherblood.cardsmatch.cardgame.AbstractMatchSystem;
import com.etherblood.cardsmatch.cardgame.match.Autowire;
import com.etherblood.firstruleset.logic.cardZones.components.BoardCardComponent;
import com.etherblood.firstruleset.logic.cardZones.events.CardZone;
import com.etherblood.firstruleset.logic.cardZones.events.CardZoneMoveEvent;
import com.etherblood.firstruleset.logic.death.DeathEvent;
import com.etherblood.entitysystem.data.EntityComponentMapReadonly;

/**
 *
 * @author Philipp
 */
public class ApplyDeathSystem extends AbstractMatchSystem<DeathEvent> {

    @Autowire
    private EntityComponentMapReadonly data;

    @Override
    public DeathEvent handle(DeathEvent event) {
        assert data.has(event.entity, BoardCardComponent.class);
        enqueueEvent(new CardZoneMoveEvent(event.entity, CardZone.BOARD, CardZone.GRAVEYARD));
        return event;
    }
}
