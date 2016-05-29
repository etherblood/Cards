package com.etherblood.firstruleset.logic.draw;

import com.etherblood.entitysystem.data.EntityId;
import com.etherblood.eventsystem.GameEvent;

/**
 *
 * @author Philipp
 */
public class RequestDrawEvent implements GameEvent {
    public final EntityId player;

    public RequestDrawEvent(EntityId playerId) {
        this.player = playerId;
    }

}
