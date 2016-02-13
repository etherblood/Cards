/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.etherblood.cardsmatch.cardgame.components.player;

import com.etherblood.entitysystem.data.EntityComponent;

/**
 *
 * @author Philipp
 */
public class ManaLimitComponent implements EntityComponent {
    public final int mana;

    public ManaLimitComponent(int mana) {
        this.mana = mana;
    }
}
