/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.etherblood.cardsmatch.cardgame.events.cardZones;

import com.etherblood.entitysystem.data.EntityId;
import com.etherblood.eventsystem.GameEvent;

/**
 *
 * @author Philipp
 */
public class CardZoneMoveEvent implements GameEvent {
    public final EntityId target;
    public final CardZone from, to;

    public CardZoneMoveEvent(EntityId target, CardZone from, CardZone to) {
        this.target = target;
        this.from = from;
        this.to = to;
    }

}
