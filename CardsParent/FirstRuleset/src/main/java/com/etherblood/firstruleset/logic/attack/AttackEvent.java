/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.etherblood.firstruleset.logic.attack;

import com.etherblood.eventsystem.GameEvent;
import com.etherblood.entitysystem.data.EntityId;

/**
 *
 * @author Philipp
 */
public class AttackEvent implements GameEvent {
    public final EntityId source, target;

    public AttackEvent(EntityId source, EntityId target) {
        this.source = source;
        this.target = target;
    }
}
