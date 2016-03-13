package com.etherblood.firstruleset.logic.battle.buffs;

import com.etherblood.cardsmatch.cardgame.components.ComponentAlias;
import com.etherblood.entitysystem.data.EntityComponent;

/**
 *
 * @author Philipp
 */
@ComponentAlias(name="regeneration")
public class RegenerationComponent implements EntityComponent {
    public final int heal;

    public RegenerationComponent(int heal) {
        this.heal = heal;
    }
}
