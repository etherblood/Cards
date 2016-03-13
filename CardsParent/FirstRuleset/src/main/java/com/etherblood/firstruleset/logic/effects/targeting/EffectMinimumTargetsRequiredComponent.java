package com.etherblood.firstruleset.logic.effects.targeting;

import com.etherblood.entitysystem.data.EntityComponent;

/**
 *
 * @author Philipp
 */
public class EffectMinimumTargetsRequiredComponent implements EntityComponent {
    public final int count;

    public EffectMinimumTargetsRequiredComponent(int count) {
        this.count = count;
    }
}
