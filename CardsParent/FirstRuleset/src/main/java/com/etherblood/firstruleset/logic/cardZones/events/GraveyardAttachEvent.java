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
public class GraveyardAttachEvent implements GameEvent {
    public final EntityId target;

    public GraveyardAttachEvent(EntityId target) {
        this.target = target;
    }
}
