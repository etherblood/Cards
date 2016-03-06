/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.etherblood.cardsmatch.cardgame.events.cardZones.systems;

import com.etherblood.cardsmatch.cardgame.AbstractMatchSystem;
import com.etherblood.match.Autowire;
import com.etherblood.cardsmatch.cardgame.components.cards.cardZone.HandCardComponent;
import com.etherblood.cardsmatch.cardgame.events.cardZones.HandAttachEvent;
import com.etherblood.entitysystem.data.EntityComponentMap;

/**
 *
 * @author Philipp
 */
public class HandAttachSystem extends AbstractMatchSystem<HandAttachEvent> {
    @Autowire
    private EntityComponentMap data;

    @Override
    public HandAttachEvent handle(HandAttachEvent event) {
        data.set(event.target, new HandCardComponent());
        return event;
    }
    
}