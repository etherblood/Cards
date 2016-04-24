package com.etherblood.firstruleset.logic.summon;

import com.etherblood.eventsystem.GameEvent;
import com.etherblood.entitysystem.data.EntityId;

/**
 *
 * @author Philipp
 */
public class SummonEvent implements GameEvent {
    public final EntityId minion;

    public SummonEvent(EntityId source) {
        this.minion = source;
    }

    @Override
    public String toString() {
        return "SummonEvent{" + "minion=" + minion + '}';
    }
}
