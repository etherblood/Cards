package com.etherblood.firstruleset.logic.player;

import com.etherblood.entitysystem.data.EntityComponent;

/**
 *
 * @author Philipp
 */
public class ManaComponent implements EntityComponent {
    public final int mana;

    public ManaComponent(int mana) {
        this.mana = mana;
    }
}
