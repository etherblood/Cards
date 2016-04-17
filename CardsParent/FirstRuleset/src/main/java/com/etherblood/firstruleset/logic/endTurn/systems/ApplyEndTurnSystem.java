package com.etherblood.firstruleset.logic.endTurn.systems;

import com.etherblood.cardsmatch.cardgame.AbstractMatchSystem;
import com.etherblood.cardscontext.Autowire;
import com.etherblood.cardsmatch.cardgame.components.player.ItsMyTurnComponent;
import com.etherblood.firstruleset.logic.endTurn.ApplyEndTurnEvent;
import com.etherblood.firstruleset.logic.endTurn.EndTurnEvent;
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
