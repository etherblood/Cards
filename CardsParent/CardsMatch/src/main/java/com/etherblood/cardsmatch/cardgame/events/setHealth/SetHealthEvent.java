/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.etherblood.cardsmatch.cardgame.events.setHealth;

import com.etherblood.eventsystem.GameEvent;
import com.etherblood.entitysystem.data.EntityId;

/**
 *
 * @author Philipp
 */
public class SetHealthEvent implements GameEvent {
    public final EntityId entity;
    public final int health;

    public SetHealthEvent(EntityId entity, int health) {
        this.entity = entity;
        this.health = health;
    }
}
