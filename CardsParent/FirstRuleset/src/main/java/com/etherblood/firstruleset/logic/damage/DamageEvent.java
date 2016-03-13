/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.etherblood.firstruleset.logic.damage;

import com.etherblood.eventsystem.GameEvent;
import com.etherblood.entitysystem.data.EntityId;

/**
 *
 * @author Philipp
 */
public class DamageEvent implements GameEvent {
    public final EntityId source, target;
    public final int damage;

    public DamageEvent(EntityId source, EntityId target, int damage) {
        this.source = source;
        this.target = target;
        this.damage = damage;
        if(damage <= 0) {
            throw new IllegalStateException();
        }
    }
}
