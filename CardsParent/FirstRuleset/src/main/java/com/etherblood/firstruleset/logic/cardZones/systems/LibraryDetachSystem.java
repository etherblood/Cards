/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.etherblood.firstruleset.logic.cardZones.systems;

import com.etherblood.cardsmatch.cardgame.AbstractMatchSystem;
import com.etherblood.cardsmatch.cardgame.match.Autowire;
import com.etherblood.firstruleset.logic.cardZones.components.LibraryCardComponent;
import com.etherblood.firstruleset.logic.cardZones.events.LibraryDetachEvent;
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