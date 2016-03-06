/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.etherblood.cardsmatch.cardgame.components.battle.stats;

import com.etherblood.entitysystem.data.EntityComponent;

/**
 *
 * @author Philipp
 */
public class ManaCostComponent implements EntityComponent {
    public final int mana;

    public ManaCostComponent(int mana) {
        this.mana = mana;
    }
}
