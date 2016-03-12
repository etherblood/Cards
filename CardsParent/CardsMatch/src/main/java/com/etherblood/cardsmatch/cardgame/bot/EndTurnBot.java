package com.etherblood.cardsmatch.cardgame.bot;

import com.etherblood.cardsmatch.cardgame.bot.commands.Command;
import com.etherblood.cardsmatch.cardgame.bot.commands.EndTurnCommandFactory;
import com.etherblood.entitysystem.data.EntityId;

/**
 *
 * @author Philipp
 */
public class EndTurnBot implements Bot {
    private final EndTurnCommandFactory factory;

    public EndTurnBot(EndTurnCommandFactory factory) {
        this.factory = factory;
    }
    @Override
    public Command think() {
        return factory.generateEndTurnCommand();
    }

    @Override
    public void clearCache() {
    }

    @Override
    public void moveNotification(EntityId effect, EntityId... targets) {
    }

}
