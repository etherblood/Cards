/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.etherblood.firstruleset.logic.mana;

import com.etherblood.entitysystem.data.EntityId;
import com.etherblood.eventsystem.GameEvent;

/**
 *
 * @author Philipp
 */
public class SetManaLimitEvent implements GameEvent {
    public final EntityId entity;
    public final int mana;

    public SetManaLimitEvent(EntityId entity, int mana) {
        this.entity = entity;
        this.mana = mana;
    }
}
