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
public class LibraryDetachEvent implements GameEvent {
    public final EntityId target;

    public LibraryDetachEvent(EntityId target) {
        this.target = target;
    }
}
