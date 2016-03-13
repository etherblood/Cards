package com.etherblood.firstruleset.logic.startTurn.systems;

import com.etherblood.cardsmatch.cardgame.AbstractMatchSystem;
import com.etherblood.cardsmatch.cardgame.match.Autowire;
import com.etherblood.firstruleset.logic.battle.buffs.RegenerationComponent;
import com.etherblood.firstruleset.logic.battle.stats.HealthComponent;
import com.etherblood.firstruleset.logic.player.OwnerComponent;
import com.etherblood.firstruleset.logic.heal.HealEvent;
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
