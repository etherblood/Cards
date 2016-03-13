package com.etherblood.firstruleset.logic.effects;

import com.etherblood.entitysystem.data.EntityId;
import com.etherblood.eventsystem.GameEvent;

/**
 *
 * @author Philipp
 */
public class TriggerEffectEvent implements GameEvent {
    public final EntityId effect;

    public TriggerEffectEvent(EntityId effect) {
        this.effect = effect;
    }
}
