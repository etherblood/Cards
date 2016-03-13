package com.etherblood.firstruleset.logic.endTurn;

import com.etherblood.entitysystem.data.EntityId;
import com.etherblood.eventsystem.GameEvent;

/**
 *
 * @author Philipp
 */
public class ApplyEndTurnEvent implements GameEvent {
    public final EntityId playerId;

    public ApplyEndTurnEvent(EntityId playerId) {
        this.playerId = playerId;
    }
}
