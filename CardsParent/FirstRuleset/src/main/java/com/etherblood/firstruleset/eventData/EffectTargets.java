package com.etherblood.firstruleset.eventData;

import com.etherblood.entitysystem.data.EntityId;

/**
 *
 * @author Philipp
 */
public class EffectTargets {
    public final EntityId[] targets;

    public EffectTargets(EntityId... targets) {
        this.targets = targets;
    }
}
