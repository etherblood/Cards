/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.etherblood.cardsmatch.cardgame.components.cards.cardZone;

import com.etherblood.entitysystem.data.EntityComponent;

/**
 *
 * @author Philipp
 */
public class LibraryCardComponent implements EntityComponent {
    public final int index;

    public LibraryCardComponent(int index) {
        this.index = index;
    }
}