package com.etherblood.cardsmatch.cardgame.events.startTurn.systems;

import com.etherblood.cardsmatch.cardgame.AbstractMatchSystem;
import com.etherblood.cardsmatch.cardgame.Autowire;
import com.etherblood.cardsmatch.cardgame.components.battle.buffs.RegenerationComponent;
import com.etherblood.cardsmatch.cardgame.components.battle.stats.HealthComponent;
import com.etherblood.cardsmatch.cardgame.components.cards.cardZone.BoardCardComponent;
import com.etherblood.cardsmatch.cardgame.components.misc.OwnerComponent;
import com.etherblood.cardsmatch.cardgame.events.heal.HealEvent;
import com.etherblood.cardsmatch.cardgame.events.startTurn.StartTurnEvent;
import com.etherblood.entitysystem.data.EntityComponentMapReadonly;
import com.etherblood.entitysystem.data.EntityId;
import com.etherblood.entitysystem.filters.AbstractComponentFieldValueFilter;
import com.etherblood.entitysystem.filters.EqualityOperator;
import com.etherblood.entitysystem.filters.FilterQuery;

/**
 *
 * @author Philipp
 */
public class RegenerationSystem extends AbstractMatchSystem<StartTurnEvent> {
    @Autowire
    private EntityComponentMapReadonly data;
    private final AbstractComponentFieldValueFilter<OwnerComponent> ownerFilter = OwnerComponent.createPlayerFilter(new EqualityOperator());
    private final FilterQuery regenerationMinionsQuery = new FilterQuery()
            .setBaseClass(BoardCardComponent.class)
            .addComponentClassFilter(RegenerationComponent.class)
            .addComponentFilter(ownerFilter)
            .addComponentClassFilter(HealthComponent.class);
    @Override
    public StartTurnEvent handle(StartTurnEvent event) {
        ownerFilter.setValue(event.player);
        for (EntityId target : regenerationMinionsQuery.list(data)) {
            enqueueEvent(new HealEvent(target, data.get(target, RegenerationComponent.class).heal));
        }
        return event;
    }
}
