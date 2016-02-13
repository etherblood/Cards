package com.etherblood.cardsmatch.cardgame.components.effects.effects;

import com.etherblood.entitysystem.data.EntityComponent;

/**
 *
 * @author Philipp
 */
public class DealDamageEffectComponent implements EntityComponent {
    public final int damage;

    public DealDamageEffectComponent(int damage) {
        this.damage = damage;
    }
}
