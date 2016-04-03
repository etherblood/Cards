package com.etherblood.cardsmatch.cardgame;

import com.etherblood.entitysystem.data.EntityId;

/**
 *
 * @author Philipp
 */
public interface CommandHandler {
    void handleCommand(EntityId player, EntityId source, EntityId... targets);
}
