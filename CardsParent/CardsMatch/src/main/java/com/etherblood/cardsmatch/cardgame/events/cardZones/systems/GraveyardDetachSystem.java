/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.etherblood.cardsmatch.cardgame.events.cardZones.systems;

import com.etherblood.cardsmatch.cardgame.AbstractMatchSystem;
import com.etherblood.cardsmatch.cardgame.Autowire;
import com.etherblood.cardsmatch.cardgame.components.cards.cardZone.GraveyardCardComponent;
import com.etherblood.cardsmatch.cardgame.events.cardZones.GraveyardDetachEvent;
import com.etherblood.entitysystem.data.EntityComponentMap;

/**
 *
 * @author Philipp
 */
public class GraveyardDetachSystem extends AbstractMatchSystem<GraveyardDetachEvent> {
    @Autowire
    private EntityComponentMap data;

    @Override
    public GraveyardDetachEvent handle(GraveyardDetachEvent event) {
        data.remove(event.target, GraveyardCardComponent.class);
        return event;
    }
    
}
