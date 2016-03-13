package com.etherblood.firstruleset.logic.effects.effects;

import com.etherblood.entitysystem.data.EntityComponent;

/**
 *
 * @author Philipp
 */
public class DrawEffectComponent implements EntityComponent {
    public final int numCards;

    public DrawEffectComponent(int numCards) {
        this.numCards = numCards;
    }
}
