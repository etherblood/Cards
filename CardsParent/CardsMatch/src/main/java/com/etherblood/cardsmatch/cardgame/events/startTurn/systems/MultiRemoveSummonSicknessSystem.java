package com.etherblood.cardsmatch.cardgame.events.startTurn.systems;

import com.etherblood.cardsmatch.cardgame.AbstractMatchSystem;
import com.etherblood.match.Autowire;
import com.etherblood.cardsmatch.cardgame.components.battle.buffs.SummonSicknessComponent;
import com.etherblood.cardsmatch.cardgame.components.cards.cardZone.BoardCardComponent;
import com.etherblood.cardsmatch.cardgame.components.misc.OwnerComponent;
import com.etherblood.cardsmatch.cardgame.events.startTurn.RemoveSummonSicknessEvent;
import com.etherblood.cardsmatch.cardgame.events.startTurn.StartTurnEvent;
import com.etherblood.entitysystem.data.EntityComponentMap;
import com.etherblood.entitysystem.data.EntityComponentMapReadonly;
import com.etherblood.entitysystem.data.EntityId;
import com.etherblood.entitysystem.filters.AbstractComponentFieldValueFilter;
import com.etherblood.entitysystem.filters.EqualityOperator;
import com.etherblood.entitysystem.filters.FilterQuery;

/**
 *
 * @author Philipp
 */
public class MultiRemoveSummonSicknessSystem extends AbstractMatchSystem<StartTurnEvent> {
    @Autowire
    private EntityComponentMapReadonly data;
    private final AbstractComponentFieldValueFilter<OwnerComponent> ownerFilter = OwnerComponent.createPlayerFilter(new EqualityOperator());
    private final FilterQuery query = new FilterQuery()
            .setBaseClass(SummonSicknessComponent.class)
            .addComponentFilter(ownerFilter)
            .addComponentClassFilter(BoardCardComponent.class);
    
    @Override
    public StartTurnEvent handle(StartTurnEvent event) {
        ownerFilter.setValue(event.player);
        for (EntityId entity : query.list(data)) {
            events.fireEvent(new RemoveSummonSicknessEvent(entity));
//            data.remove(entity, SummonSicknessComponent.class);
        }
        return event;
    }

}
