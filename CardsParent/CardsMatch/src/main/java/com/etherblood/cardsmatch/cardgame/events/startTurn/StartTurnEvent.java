/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.etherblood.cardsmatch.cardgame.events.startTurn;

import com.etherblood.eventsystem.GameEvent;
import com.etherblood.entitysystem.data.EntityId;

/**
 *
 * @author Philipp
 */
public class StartTurnEvent implements GameEvent {
    public final EntityId player;

    public StartTurnEvent(EntityId playerId) {
        this.player = playerId;
    }
}
