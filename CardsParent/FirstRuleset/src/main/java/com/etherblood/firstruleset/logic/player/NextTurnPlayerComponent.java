package com.etherblood.firstruleset.logic.player;

import com.etherblood.entitysystem.data.EntityComponent;
import com.etherblood.entitysystem.data.EntityId;

/**
 *
 * @author Philipp
 */
public class NextTurnPlayerComponent implements EntityComponent {
    public final EntityId player;

    public NextTurnPlayerComponent(EntityId playerId) {
        this.player = playerId;
    }
}
