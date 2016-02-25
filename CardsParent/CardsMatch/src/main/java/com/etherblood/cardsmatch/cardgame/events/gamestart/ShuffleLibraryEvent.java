package com.etherblood.cardsmatch.cardgame.events.gamestart;

import com.etherblood.entitysystem.data.EntityId;
import com.etherblood.eventsystem.GameEvent;

/**
 *
 * @author Philipp
 */
public class ShuffleLibraryEvent implements GameEvent {
public final EntityId player;

    public ShuffleLibraryEvent(EntityId player) {
        this.player = player;
    }
}
