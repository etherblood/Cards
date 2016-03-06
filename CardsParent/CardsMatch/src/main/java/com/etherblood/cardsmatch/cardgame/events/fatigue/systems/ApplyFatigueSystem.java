/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.etherblood.cardsmatch.cardgame.events.fatigue.systems;

import com.etherblood.cardsmatch.cardgame.AbstractMatchSystem;
import com.etherblood.match.Autowire;
import com.etherblood.cardsmatch.cardgame.components.battle.hero.HeroComponent;
import com.etherblood.cardsmatch.cardgame.components.misc.OwnerComponent;
import com.etherblood.cardsmatch.cardgame.components.player.FatigueCounterComponent;
import com.etherblood.cardsmatch.cardgame.events.damage.DamageEvent;
import com.etherblood.cardsmatch.cardgame.events.fatigue.FatigueEvent;
import com.etherblood.entitysystem.data.EntityComponentMap;
import com.etherblood.entitysystem.data.EntityId;
import com.etherblood.entitysystem.filters.AbstractComponentFieldValueFilter;
import com.etherblood.entitysystem.filters.EqualityOperator;
import com.etherblood.entitysystem.filters.FilterQuery;

/**
 *
 * @author Philipp
 */
public class ApplyFatigueSystem extends AbstractMatchSystem<FatigueEvent> {
    @Autowire
    private EntityComponentMap data;

    private final AbstractComponentFieldValueFilter<OwnerComponent> ownerFilter = OwnerComponent.createPlayerFilter(new EqualityOperator());
    private final FilterQuery heroQuery = new FilterQuery()
            .setBaseClass(HeroComponent.class)
            .addComponentFilter(ownerFilter);
    
    @Override
    public FatigueEvent handle(FatigueEvent event) {
        FatigueCounterComponent counter = data.get(event.player, FatigueCounterComponent.class);
        int fatigue = counter == null? 0: counter.count;
        fatigue++;
        data.set(event.player, new FatigueCounterComponent(fatigue));
        ownerFilter.setValue(event.player);
        for (EntityId entityId : heroQuery.list(data)) {
            enqueueEvent(new DamageEvent(entityId, entityId, fatigue));
        }
        return event;
    }
    
}
