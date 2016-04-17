/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.etherblood.firstruleset.logic.draw.systems;

import com.etherblood.cardsmatch.cardgame.AbstractMatchSystem;
import com.etherblood.cardscontext.Autowire;
import com.etherblood.firstruleset.logic.player.OwnerComponent;
import com.etherblood.firstruleset.logic.draw.DrawEvent;
import com.etherblood.firstruleset.logic.fatigue.FatigueEvent;
import com.etherblood.firstruleset.logic.draw.RequestDrawEvent;
import com.etherblood.entitysystem.data.EntityComponentMapReadonly;
import com.etherblood.entitysystem.data.EntityId;
import com.etherblood.entitysystem.filters.AbstractComponentFieldValueFilter;
import com.etherblood.entitysystem.filters.EqualityOperator;
import com.etherblood.entitysystem.filters.FilterQuery;
import com.etherblood.firstruleset.logic.cardZones.components.LibraryCardComponent;
import java.util.Comparator;

/**
 *
 * @author Philipp
 */
public class RequestDrawSystem extends AbstractMatchSystem<RequestDrawEvent> {
    private static final Comparator<LibraryCardComponent> comparator = new Comparator<LibraryCardComponent>() {
            @Override
            public int compare(LibraryCardComponent o1, LibraryCardComponent o2) {
                return o2.index - o1.index;
            }
        };
    @Autowire
    private EntityComponentMapReadonly data;
    private final AbstractComponentFieldValueFilter<OwnerComponent> ownerFilter = OwnerComponent.createPlayerFilter(new EqualityOperator());
    private final FilterQuery query = new FilterQuery().setBaseClass(LibraryCardComponent.class).addComponentFilter(ownerFilter);
    
    @Override
    public RequestDrawEvent handle(RequestDrawEvent event) {
        ownerFilter.setValue(event.player);
        EntityId card = query.max(data, LibraryCardComponent.class, comparator);
        if(card == null) {
            enqueueEvent(new FatigueEvent(event.player));
            return null;
        }
        enqueueEvent(new DrawEvent(event.player, card));
        return event;
    }
    
}
