package com.etherblood.firstruleset.logic.battle.buffs;

import com.etherblood.entitysystem.data.EntityComponent;

/**
 *
 * @author Philipp
 */
public class AttackCountComponent implements EntityComponent {
    public final int numAttacks;

    public AttackCountComponent(int numAttacks) {
        this.numAttacks = numAttacks;
    }
}
