/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.etherblood.firstruleset.logic.player;

import com.etherblood.entitysystem.data.EntityComponent;

/**
 *
 * @author Philipp
 */
public class FatigueCounterComponent implements EntityComponent {
    public final int count;

    public FatigueCounterComponent(int count) {
        this.count = count;
    }
}
