package com.etherblood.firstruleset.logic.startTurn.systems;

import com.etherblood.cardsmatch.cardgame.AbstractMatchSystem;
import com.etherblood.cardscontext.Autowire;
import com.etherblood.firstruleset.logic.battle.buffs.AttackCountComponent;
import com.etherblood.firstruleset.logic.player.OwnerComponent;
import com.etherblood.firstruleset.logic.startTurn.StartTurnEvent;
import com.etherblood.entitysystem.data.EntityComponentMap;
import com.etherblood.entitysystem.data.EntityId;
import com.etherblood.entitysystem.filters.AbstractComponentFieldValueFilter;
import com.etherblood.entitysystem.filters.EqualityOperator;
import com.etherblood.entitysystem.filters.FilterQuery;
import com.etherblood.firstruleset.logic.cardZones.components.BoardCardComponent;

/**
 *
 * @author Philipp
 */
public class RemoveAttackCountSystem extends AbstractMatchSystem<StartTurnEvent> {
    @Autowire
    private EntityComponentMap data;
    private final AbstractComponentFieldValueFilter<OwnerComponent> ownerFilter = OwnerComponent.createPlayerFilter(new EqualityOperator());
    private final FilterQuery query = new FilterQuery()
            .setBaseClass(AttackCountComponent.class)
            .addComponentFilter(ownerFilter)
            .addComponentClassFilter(BoardCardComponent.class);
    
    @Override
    public StartTurnEvent handle(StartTurnEvent event) {
        ownerFilter.setValue(event.player);
        for (EntityId entity : query.list(data)) {
            data.remove(entity, AttackCountComponent.class);
        }
        return event;
    }
}
