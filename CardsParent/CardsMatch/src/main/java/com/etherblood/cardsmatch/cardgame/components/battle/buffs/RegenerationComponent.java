package com.etherblood.cardsmatch.cardgame.components.battle.buffs;

import com.etherblood.entitysystem.data.EntityComponent;

/**
 *
 * @author Philipp
 */
public class RegenerationComponent implements EntityComponent {
    public final int heal;

    public RegenerationComponent(int heal) {
        this.heal = heal;
    }
}
