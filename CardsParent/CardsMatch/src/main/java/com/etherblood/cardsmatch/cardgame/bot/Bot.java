package com.etherblood.cardsmatch.cardgame.bot;

import com.etherblood.cardsmatch.cardgame.bot.commands.Command;
import com.etherblood.entitysystem.data.EntityId;

/**
 *
 * @author Philipp
 */
public interface Bot {

    Command think();
    void moveNotification(EntityId effect, EntityId... targets);
    void clearCache();
    
}
