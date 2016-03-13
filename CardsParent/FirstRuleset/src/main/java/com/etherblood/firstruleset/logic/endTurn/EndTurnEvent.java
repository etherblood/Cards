/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.etherblood.firstruleset.logic.endTurn;

import com.etherblood.eventsystem.GameEvent;
import com.etherblood.entitysystem.data.EntityId;

/**
 *
 * @author Philipp
 */
public class EndTurnEvent implements GameEvent {
    public final EntityId player;

    public EndTurnEvent(EntityId playerId) {
        this.player = playerId;
    }
}
