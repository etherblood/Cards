/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.etherblood.cardsmatch.cardgame.events.fatigue;

import com.etherblood.entitysystem.data.EntityId;
import com.etherblood.eventsystem.GameEvent;

/**
 *
 * @author Philipp
 */
public class FatigueEvent implements GameEvent {
    public final EntityId player;

    public FatigueEvent(EntityId player) {
        this.player = player;
    }
}
