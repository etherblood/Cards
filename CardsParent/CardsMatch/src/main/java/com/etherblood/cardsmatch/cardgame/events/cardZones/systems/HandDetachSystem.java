/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.etherblood.cardsmatch.cardgame.events.cardZones.systems;

import com.etherblood.cardsmatch.cardgame.AbstractMatchSystem;
import com.etherblood.cardsmatch.cardgame.Autowire;
import com.etherblood.cardsmatch.cardgame.components.cards.cardZone.HandCardComponent;
import com.etherblood.cardsmatch.cardgame.events.cardZones.HandDetachEvent;
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