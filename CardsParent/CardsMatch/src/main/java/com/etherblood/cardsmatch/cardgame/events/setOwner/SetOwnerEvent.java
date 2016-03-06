/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.etherblood.cardsmatch.cardgame.events.setOwner;

import com.etherblood.entitysystem.data.EntityId;
import com.etherblood.eventsystem.GameEvent;

/**
 *
 * @author Philipp
 */
public class SetOwnerEvent implements GameEvent {
    public final EntityId target, owner;

    public SetOwnerEvent(EntityId target, EntityId owner) {
        this.target = target;
        this.owner = owner;
    }
}
