/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.etherblood.firstruleset.logic.cardZones.systems;

import com.etherblood.cardsmatch.cardgame.AbstractMatchSystem;
import com.etherblood.cardsmatch.cardgame.match.Autowire;
import com.etherblood.firstruleset.logic.cardZones.components.GraveyardCardComponent;
import com.etherblood.firstruleset.logic.cardZones.events.GraveyardDetachEvent;
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
