package com.etherblood.firstruleset.logic.damage;

import com.etherblood.entitysystem.data.EntityId;
import com.etherblood.eventsystem.GameEvent;

/**
 *
 * @author Philipp
 */
public class SetDivineShieldEvent implements GameEvent {
    public final EntityId target;
    public final boolean value;

    public SetDivineShieldEvent(EntityId target, boolean value) {
        this.target = target;
        this.value = value;
    }

}
