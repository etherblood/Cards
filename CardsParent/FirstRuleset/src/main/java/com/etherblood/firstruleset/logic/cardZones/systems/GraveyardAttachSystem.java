/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.etherblood.firstruleset.logic.cardZones.systems;

import com.etherblood.cardsmatch.cardgame.AbstractMatchSystem;
import com.etherblood.cardsmatch.cardgame.match.Autowire;
import com.etherblood.firstruleset.logic.cardZones.components.GraveyardCardComponent;
import com.etherblood.firstruleset.logic.cardZones.events.GraveyardAttachEvent;
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
