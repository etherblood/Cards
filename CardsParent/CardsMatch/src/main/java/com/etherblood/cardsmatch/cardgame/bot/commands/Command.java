package com.etherblood.cardsmatch.cardgame.bot.commands;

import com.etherblood.entitysystem.data.EntityId;

/**
 *
 * @author Philipp
 */
public class Command {
    public final EntityId effect;
    public final EntityId[] targets;

    public Command(EntityId effect, EntityId... targets) {
        this.effect = effect;
        this.targets = targets;
    }
}
