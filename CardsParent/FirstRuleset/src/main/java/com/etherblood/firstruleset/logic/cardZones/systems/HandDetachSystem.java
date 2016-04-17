/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.etherblood.firstruleset.logic.cardZones.systems;

import com.etherblood.cardsmatch.cardgame.AbstractMatchSystem;
import com.etherblood.cardscontext.Autowire;
import com.etherblood.firstruleset.logic.cardZones.components.HandCardComponent;
import com.etherblood.firstruleset.logic.cardZones.events.HandDetachEvent;
import com.etherblood.entitysystem.data.EntityComponentMap;

/**
 *
 * @author Philipp
 */
public class HandDetachSystem extends AbstractMatchSystem<HandDetachEvent> {
    @Autowire
    private EntityComponentMap data;

    @Override
    public HandDetachEvent handle(HandDetachEvent event) {
        data.remove(event.target, HandCardComponent.class);
        return event;
    }
    
}