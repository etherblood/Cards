package com.etherblood.cardsmatch.cardgame.events.gameover.systems;

import com.etherblood.cardsmatch.cardgame.AbstractMatchSystem;
import com.etherblood.cardscontext.Autowire;
import com.etherblood.cardsmatch.cardgame.components.misc.MatchEndedComponent;
import com.etherblood.cardsmatch.cardgame.components.player.PlayerComponent;
import com.etherblood.cardsmatch.cardgame.components.player.WinnerComponent;
import com.etherblood.cardsmatch.cardgame.events.gameover.PlayerLostEvent;
import com.etherblood.entitysystem.data.EntityComponentMap;
import com.etherblood.entitysystem.data.EntityId;
import com.etherblood.entitysystem.data.EntityIdFactory;
import java.util.Set;

/**
 *
 * @author Philipp
 */
public class EndMatchSystem extends AbstractMatchSystem<PlayerLostEvent> {
    @Autowire
    private EntityComponentMap data;
    @Autowire
    private EntityIdFactory idFactory;

    @Override
    public PlayerLostEvent handle(PlayerLostEvent event) {
        Set<EntityId> players = data.entities(PlayerComponent.class);
        for (EntityId player : players) {
            if(player != event.player) {
                data.set(player, new WinnerComponent());
            }
        }
        data.set(idFactory.createEntity(), new MatchEndedComponent());
        return event;
    }

}
