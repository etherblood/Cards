package com.etherblood.cardsmatch.cardgame.components.effects.effects;

import com.etherblood.entitysystem.data.EntityComponent;

/**
 *
 * @author Philipp
 */
public class DealRandomDamageEffectComponent implements EntityComponent {
    public final int offset, rngRange;

    public DealRandomDamageEffectComponent(int offset, int rngRange) {
        this.offset = offset;
        this.rngRange = rngRange;
    }
    
}
