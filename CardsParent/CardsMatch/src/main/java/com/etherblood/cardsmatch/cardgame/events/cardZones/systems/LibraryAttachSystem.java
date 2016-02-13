/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.etherblood.cardsmatch.cardgame.events.cardZones.systems;

import com.etherblood.cardsmatch.cardgame.AbstractMatchSystem;
import com.etherblood.cardsmatch.cardgame.Autowire;
import com.etherblood.cardsmatch.cardgame.components.cards.cardZone.LibraryCardComponent;
import com.etherblood.cardsmatch.cardgame.components.misc.OwnerComponent;
import com.etherblood.cardsmatch.cardgame.events.cardZones.LibraryAttachEvent;
import com.etherblood.entitysystem.data.EntityComponentMap;
import com.etherblood.entitysystem.filters.AbstractComponentFieldValueFilter;
import com.etherblood.entitysystem.filters.EqualityOperator;
import com.etherblood.entitysystem.filters.FilterQuery;

/**
 *
 * @author Philipp
 */
public class LibraryAttachSystem extends AbstractMatchSystem<LibraryAttachEvent> {
    @Autowire
    private EntityComponentMap data;
    
    private final AbstractComponentFieldValueFilter<OwnerComponent> ownerFilter = OwnerComponent.createPlayerFilter(new EqualityOperator());
    private final FilterQuery libraryCards = new FilterQuery()
            .setBaseClass(LibraryCardComponent.class)
            .addComponentFilter(ownerFilter);

    @Override
    public LibraryAttachEvent handle(LibraryAttachEvent event) {
        ownerFilter.setValue(data.get(event.target, OwnerComponent.class).player);
        data.set(event.target, new LibraryCardComponent(libraryCards.count(data)));
        return event;
    }
    
}