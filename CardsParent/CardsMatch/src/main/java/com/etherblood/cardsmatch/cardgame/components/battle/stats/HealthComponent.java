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
public class HealthComponent implements EntityComponent {
    public final int health;

    public HealthComponent(int health) {
        this.health = health;
    }
}
