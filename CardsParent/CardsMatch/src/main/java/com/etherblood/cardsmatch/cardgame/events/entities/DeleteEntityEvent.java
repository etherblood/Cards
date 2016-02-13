package com.etherblood.cardsmatch.cardgame.events.entities;

import com.etherblood.entitysystem.data.EntityId;
import com.etherblood.eventsystem.GameEvent;

/**
 *
 * @author Philipp
 */
public class DeleteEntityEvent implements GameEvent {
    public final EntityId target;

    public DeleteEntityEvent(EntityId target) {
        this.target = target;
    }
}
