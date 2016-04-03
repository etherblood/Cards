package com.etherblood.firstruleset.logic.templates.patron;

import com.etherblood.entitysystem.data.EntityId;
import com.etherblood.eventsystem.GameEvent;

/**
 *
 * @author Philipp
 */
public class PatronSurvivalCheckEvent implements GameEvent {
    public final EntityId target;

    public PatronSurvivalCheckEvent(EntityId target) {
        this.target = target;
    }
}
