package com.etherblood.cardsmatch.cardgame.events.entities.systems;

import com.etherblood.cardsmatch.cardgame.AbstractMatchSystem;
import com.etherblood.match.Autowire;
import com.etherblood.cardsmatch.cardgame.events.entities.DeleteEntityEvent;
import com.etherblood.entitysystem.data.EntityComponentMap;

/**
 *
 * @author Philipp
 */
public class DeleteEntitySystem extends AbstractMatchSystem<DeleteEntityEvent> {
    @Autowire
    private EntityComponentMap data;

    @Override
    public DeleteEntityEvent handle(DeleteEntityEvent event) {
        data.clear(event.target);
        return event;
    }

}
