package com.etherblood.firstruleset.logic.effects;

import com.etherblood.entitysystem.data.EntityId;
import com.etherblood.eventsystem.GameEvent;
import java.util.Arrays;

/**
 *
 * @author Philipp
 */
public class TriggerEffectEvent implements GameEvent {
    public final EntityId effect;
    public final EntityId[] targets;

    public TriggerEffectEvent(EntityId effect, EntityId... targets) {
        this.effect = effect;
        this.targets = targets;
    }

    @Override
    public String toString() {
        return "TriggerEffectEvent{" + "effect=" + effect + ", targets=" + Arrays.toString(targets) + '}';
    }
}
