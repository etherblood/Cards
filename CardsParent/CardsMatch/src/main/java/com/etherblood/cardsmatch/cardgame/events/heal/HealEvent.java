/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.etherblood.cardsmatch.cardgame.events.heal;

import com.etherblood.eventsystem.GameEvent;
import com.etherblood.entitysystem.data.EntityId;

/**
 *
 * @author Philipp
 */
public class HealEvent implements GameEvent {
    public final EntityId target;
    public final int heal;

    public HealEvent(EntityId target, int heal) {
        this.target = target;
        this.heal = heal;
        if(heal <= 0) {
            throw new IllegalStateException();
        }
    }
}
