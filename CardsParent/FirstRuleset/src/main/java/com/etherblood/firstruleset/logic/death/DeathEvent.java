/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.etherblood.firstruleset.logic.death;

import com.etherblood.eventsystem.GameEvent;
import com.etherblood.entitysystem.data.EntityId;

/**
 *
 * @author Philipp
 */
public class DeathEvent implements GameEvent {
    public final EntityId entity;

    public DeathEvent(EntityId entity) {
        this.entity = entity;
    }
}
