package com.etherblood.cardsmatch.cardgame.events.systems;

import com.etherblood.cardsmatch.cardgame.AbstractMatchSystem;
import com.etherblood.cardsmatch.cardgame.Autowire;
import com.etherblood.cardsmatch.cardgame.RngFactory;
import com.etherblood.cardsmatch.cardgame.components.cards.cardZone.LibraryCardComponent;
import com.etherblood.cardsmatch.cardgame.components.misc.OwnerComponent;
import com.etherblood.cardsmatch.cardgame.events.ShuffleLibraryEvent;
import com.etherblood.entitysystem.data.EntityComponentMap;
import com.etherblood.entitysystem.data.EntityId;
import com.etherblood.entitysystem.filters.AbstractComponentFieldValueFilter;
import com.etherblood.entitysystem.filters.EqualityOperator;
import com.etherblood.entitysystem.filters.FilterQuery;
import java.util.List;

/**
 *
 * @author Philipp
 */
public class ShuffleLibrarySystem extends AbstractMatchSystem<ShuffleLibraryEvent> {

    @Autowire private EntityComponentMap data;
    @Autowire private RngFactory rng;
    private final AbstractComponentFieldValueFilter<OwnerComponent> ownerFilter = OwnerComponent.createPlayerFilter(new EqualityOperator());
    private final FilterQuery query = new FilterQuery().setBaseClass(LibraryCardComponent.class).addComponentFilter(ownerFilter);
    
    @Override
    public ShuffleLibraryEvent handle(ShuffleLibraryEvent event) {
        ownerFilter.setValue(event.player);
        List<EntityId> list = query.list(data);
        shuffle(list);
        for (int i = 0; i < list.size(); i++) {
            data.set(list.get(i), new LibraryCardComponent(i));
        }
        return event;
    }
    
    private void shuffle(List<EntityId> list) {
        for (int i = list.size(); i > 1; ) {
            int j = rng.nextInt(i);
            i--;
            swap(list, i, j);
        }
    }
    
    private void swap(List<EntityId> list, int i , int j) {
        EntityId tmp = list.get(i);
        list.set(i, list.get(j));
        list.set(j, tmp);
    }

}
