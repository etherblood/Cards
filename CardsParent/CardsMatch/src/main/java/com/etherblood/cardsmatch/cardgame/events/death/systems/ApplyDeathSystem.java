/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.etherblood.cardsmatch.cardgame.events.death.systems;

import com.etherblood.cardsmatch.cardgame.AbstractMatchSystem;
import com.etherblood.match.Autowire;
import com.etherblood.cardsmatch.cardgame.components.cards.cardZone.BoardCardComponent;
import com.etherblood.cardsmatch.cardgame.events.cardZones.CardZone;
import com.etherblood.cardsmatch.cardgame.events.cardZones.CardZoneMoveEvent;
import com.etherblood.cardsmatch.cardgame.events.death.DeathEvent;
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
