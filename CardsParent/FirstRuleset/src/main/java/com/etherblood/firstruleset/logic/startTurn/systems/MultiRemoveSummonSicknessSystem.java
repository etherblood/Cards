package com.etherblood.firstruleset.logic.startTurn.systems;

import com.etherblood.cardsmatch.cardgame.AbstractMatchSystem;
import com.etherblood.cardsmatch.cardgame.match.Autowire;
import com.etherblood.firstruleset.logic.battle.buffs.SummonSicknessComponent;
import com.etherblood.firstruleset.logic.player.OwnerComponent;
import com.etherblood.firstruleset.logic.startTurn.RemoveSummonSicknessEvent;
import com.etherblood.firstruleset.logic.startTurn.StartTurnEvent;
import com.etherblood.entitysystem.data.EntityComponentMapReadonly;
import com.etherblood.entitysystem.data.EntityId;
import com.etherblood.entitysystem.filters.AbstractComponentFieldValueFilter;
import com.etherblood.entitysystem.filters.EqualityOperator;
import com.etherblood.entitysystem.filters.FilterQuery;
import com.etherblood.firstruleset.logic.cardZones.components.BoardCardComponent;

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
