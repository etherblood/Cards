/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.etherblood.firstruleset.logic.cardZones.events;

import com.etherblood.entitysystem.data.EntityId;
import com.etherblood.eventsystem.GameEvent;

/**
 *
 * @author Philipp
 */
public class GraveyardDetachEvent implements GameEvent {
    public final EntityId target;

    public GraveyardDetachEvent(EntityId target) {
        this.target = target;
    }
}
