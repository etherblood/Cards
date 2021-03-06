package com.etherblood.firstruleset.logic.draw;

import com.etherblood.entitysystem.data.EntityId;
import com.etherblood.eventsystem.GameEvent;

/**
 *
 * @author Philipp
 */
public class DrawEvent implements GameEvent {
    public final EntityId playerId, card;

    public DrawEvent(EntityId playerId, EntityId cardId) {
        this.playerId = playerId;
        this.card = cardId;
    }

}
