package com.etherblood.cardsmatch.cardgame.events.entities.systems;

import com.etherblood.cardsmatch.cardgame.AbstractMatchSystem;
import com.etherblood.cardsmatch.cardgame.Autowire;
import com.etherblood.cardsmatch.cardgame.components.effects.EffectTriggerEntityComponent;
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
public class DeleteEntityTriggerChildsSystem extends AbstractMatchSystem<DeleteEntityEvent> {
    @Autowire
    private EntityComponentMapReadonly data;
    private final AbstractComponentFieldValueFilter<EffectTriggerEntityComponent> parentFilter = EffectTriggerEntityComponent.createTriggerFilter(new EqualityOperator());
    private final FilterQuery childsQuery = new FilterQuery()
            .setBaseClass(EffectTriggerEntityComponent.class)
            .addComponentFilter(parentFilter);
    
    @Override
    public DeleteEntityEvent handle(DeleteEntityEvent event) {
        parentFilter.setValue(event.target);
        for (EntityId child : childsQuery.list(data)) {
            enqueueEvent(new DeleteEntityEvent(child));
        }
        return event;
    }

}
