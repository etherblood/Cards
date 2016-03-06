/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.etherblood.cardsmatch.cardgame.events.cardZones.systems;

import com.etherblood.cardsmatch.cardgame.AbstractMatchSystem;
import com.etherblood.match.Autowire;
import com.etherblood.cardsmatch.cardgame.components.cards.cardZone.LibraryCardComponent;
import com.etherblood.cardsmatch.cardgame.events.cardZones.LibraryDetachEvent;
import com.etherblood.entitysystem.data.EntityComponentMap;

/**
 *
 * @author Philipp
 */
public class LibraryDetachSystem extends AbstractMatchSystem<LibraryDetachEvent> {
    @Autowire
    private EntityComponentMap data;

    @Override
    public LibraryDetachEvent handle(LibraryDetachEvent event) {
        data.remove(event.target, LibraryCardComponent.class);
        return event;
    }
    
}