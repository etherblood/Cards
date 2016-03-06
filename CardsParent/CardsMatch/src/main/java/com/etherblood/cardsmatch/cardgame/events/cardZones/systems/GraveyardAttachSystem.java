/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.etherblood.cardsmatch.cardgame.events.cardZones.systems;

import com.etherblood.cardsmatch.cardgame.AbstractMatchSystem;
import com.etherblood.match.Autowire;
import com.etherblood.cardsmatch.cardgame.components.cards.cardZone.GraveyardCardComponent;
import com.etherblood.cardsmatch.cardgame.events.cardZones.GraveyardAttachEvent;
import com.etherblood.entitysystem.data.EntityComponentMap;

/**
 *
 * @author Philipp
 */
public class GraveyardAttachSystem extends AbstractMatchSystem<GraveyardAttachEvent> {
    @Autowire
    private EntityComponentMap data;

    @Override
    public GraveyardAttachEvent handle(GraveyardAttachEvent event) {
        data.set(event.target, new GraveyardCardComponent());
        return event;
    }
    
}
