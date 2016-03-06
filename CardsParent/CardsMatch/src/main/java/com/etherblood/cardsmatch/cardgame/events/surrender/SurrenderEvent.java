/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.etherblood.cardsmatch.cardgame.events.surrender;

import com.etherblood.entitysystem.data.EntityId;
import com.etherblood.eventsystem.GameEvent;

/**
 *
 * @author Philipp
 */
public class SurrenderEvent implements GameEvent {
    public final EntityId player;

    public SurrenderEvent(EntityId playerId) {
        this.player = playerId;
    }
}
