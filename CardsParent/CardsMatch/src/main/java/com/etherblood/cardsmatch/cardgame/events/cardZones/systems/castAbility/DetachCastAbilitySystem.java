package com.etherblood.cardsmatch.cardgame.events.cardZones.systems.castAbility;

import com.etherblood.cardsmatch.cardgame.AbstractMatchSystem;
import com.etherblood.match.Autowire;
import com.etherblood.cardsmatch.cardgame.components.cards.DeleteOnTriggerRemovedFromHand;
import com.etherblood.cardsmatch.cardgame.components.effects.EffectTriggerEntityComponent;
import com.etherblood.cardsmatch.cardgame.events.cardZones.HandDetachEvent;
import com.etherblood.cardsmatch.cardgame.events.entities.DeleteEntityEvent;
import com.etherblood.entitysystem.data.EntityComponentMapReadonly;
import com.etherblood.entitysystem.data.EntityId;
import com.etherblood.entitysystem.filters.AbstractComponentFieldValueFilter;
import com.etherblood.entitysystem.filters.EqualityOperator;
import com.etherblood.entitysystem.filters.FilterQuery;

/**
 *
 * @author Philipp
 */
public class DetachCastAbilitySystem extends AbstractMatchSystem<HandDetachEvent> {
    @Autowire
    private EntityComponentMapReadonly data;
    
    AbstractComponentFieldValueFilter<EffectTriggerEntityComponent> filter = EffectTriggerEntityComponent.createTriggerFilter(new EqualityOperator());
    FilterQuery query = new FilterQuery()
            .setBaseClass(DeleteOnTriggerRemovedFromHand.class)
            .addComponentFilter(filter);

    @Override
    public HandDetachEvent handle(HandDetachEvent event) {
        filter.setValue(event.target);
        for (EntityId entity : query.list(data)) {
            enqueueEvent(new DeleteEntityEvent(entity));
        }
        return event;
    }

}
