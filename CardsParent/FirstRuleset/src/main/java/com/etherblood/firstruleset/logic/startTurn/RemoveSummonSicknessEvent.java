package com.etherblood.firstruleset.logic.startTurn;

import com.etherblood.entitysystem.data.EntityId;
import com.etherblood.eventsystem.GameEvent;

/**
 *
 * @author Philipp
 */
public class RemoveSummonSicknessEvent implements GameEvent {
    public final EntityId target;

    public RemoveSummonSicknessEvent(EntityId target) {
        this.target = target;
    }
}
