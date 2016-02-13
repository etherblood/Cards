package com.etherblood.cardsmatch.cardgame.events.effects;

import com.etherblood.entitysystem.data.EntityId;
import com.etherblood.eventsystem.GameEvent;

/**
 *
 * @author Philipp
 */
public class TargetedTriggerEffectEvent implements GameEvent {
    public final EntityId effect;
    public final EntityId[] targets;

    public TargetedTriggerEffectEvent(EntityId effect, EntityId[] targets) {
        this.effect = effect;
        this.targets = targets;
    }
}
