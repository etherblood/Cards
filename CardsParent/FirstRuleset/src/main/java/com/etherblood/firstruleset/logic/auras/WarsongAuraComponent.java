package com.etherblood.firstruleset.logic.auras;

import com.etherblood.cardsmatch.cardgame.components.ComponentAlias;
import com.etherblood.entitysystem.data.EntityComponent;

/**
 *
 * @author Philipp
 */
@ComponentAlias(name = "warsongAura")
public class WarsongAuraComponent implements EntityComponent {

    public final int maxAttack;

    public WarsongAuraComponent(int maxAttack) {
        this.maxAttack = maxAttack;
    }

}
