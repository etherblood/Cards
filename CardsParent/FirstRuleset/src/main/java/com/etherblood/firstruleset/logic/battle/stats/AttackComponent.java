package com.etherblood.firstruleset.logic.battle.stats;

import com.etherblood.cardsmatch.cardgame.components.ComponentAlias;
import com.etherblood.entitysystem.data.EntityComponent;

/**
 *
 * @author Philipp
 */
@ComponentAlias(name="attack")
public class AttackComponent implements EntityComponent {
    public final int attack;

    public AttackComponent(int attack) {
        this.attack = attack;
    }
}
