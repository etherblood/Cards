/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.etherblood.firstruleset.logic.cardZones.systems;

import com.etherblood.cardsmatch.cardgame.AbstractMatchSystem;
import com.etherblood.cardsmatch.cardgame.match.Autowire;
import com.etherblood.firstruleset.logic.cardZones.components.HandCardComponent;
import com.etherblood.firstruleset.logic.cardZones.events.HandAttachEvent;
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