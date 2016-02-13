package com.etherblood.cardsmatch.cardgame.events.endTurn.systems;

import com.etherblood.cardsmatch.cardgame.AbstractMatchSystem;
import com.etherblood.cardsmatch.cardgame.Autowire;
import com.etherblood.cardsmatch.cardgame.components.player.ItsMyTurnComponent;
import com.etherblood.cardsmatch.cardgame.events.endTurn.ApplyEndTurnEvent;
import com.etherblood.cardsmatch.cardgame.events.endTurn.EndTurnEvent;
import com.etherblood.entitysystem.data.EntityComponentMap;

/**
 *
 * @author Philipp
 */
public class ApplyEndTurnSystem extends AbstractMatchSystem<ApplyEndTurnEvent> {
    @Autowire
    private EntityComponentMap data;

    @Override
    public ApplyEndTurnEvent handle(ApplyEndTurnEvent event) {
        if(data.remove(event.playerId, ItsMyTurnComponent.class) == null) {
            throw new RuntimeException("Removed " + EndTurnEvent.class.getSimpleName() + " because it was not the players turn.");
        }
        return event;
    }
}
