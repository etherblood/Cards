package com.etherblood.firstruleset.logic.cardZones.systems.castAbility;

import com.etherblood.cardsmatch.cardgame.AbstractMatchSystem;
import com.etherblood.cardscontext.Autowire;
import com.etherblood.firstruleset.logic.cards.DeleteOnTriggerRemovedFromHand;
import com.etherblood.firstruleset.logic.effects.EffectTriggerEntityComponent;
import com.etherblood.firstruleset.logic.cardZones.events.HandDetachEvent;
import com.etherblood.firstruleset.logic.entities.DeleteEntityEvent;
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
