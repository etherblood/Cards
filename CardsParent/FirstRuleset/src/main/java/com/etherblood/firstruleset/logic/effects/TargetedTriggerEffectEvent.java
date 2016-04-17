package com.etherblood.firstruleset.logic.effects;

import com.etherblood.entitysystem.data.EntityId;
import com.etherblood.eventsystem.GameEvent;

/**
 *
 * @author Philipp
 */
public class TargetedTriggerEffectEvent implements GameEvent {
    public final EntityId player;
    public final EntityId effect;
    public final EntityId[] targets;

    public TargetedTriggerEffectEvent(EntityId player, EntityId effect, EntityId[] targets) {
        this.player = player;
        this.effect = effect;
        this.targets = targets;
    }
}
