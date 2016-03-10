/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.etherblood.cardsmatch.cardgame.components.battle.stats;

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
