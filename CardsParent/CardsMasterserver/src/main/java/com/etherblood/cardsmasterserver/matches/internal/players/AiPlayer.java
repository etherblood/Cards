package com.etherblood.cardsmasterserver.matches.internal.players;

import com.etherblood.cardsmasterserver.matches.internal.MatchContextWrapper;
import com.etherblood.cardsmatch.cardgame.bot.Bot;
import com.etherblood.cardsmatch.cardgame.bot.commands.Command;
import com.etherblood.cardsmatch.cardgame.components.player.ItsMyTurnComponent;
import com.etherblood.entitysystem.data.EntityId;

/**
 *
 * @author Philipp
 */
public class AiPlayer extends AbstractPlayer {

    private Bot bot;
    

    public AiPlayer(EntityId player) {
        super(player);
    }
    
    public void clearCache() {
        bot.clearCache();
    }

    public void compute() {
        MatchContextWrapper matchWrapper = getMatch();
        synchronized (matchWrapper) {
            if (!matchWrapper.hasMatchEnded() && matchWrapper.getData().has(getPlayer(), ItsMyTurnComponent.class)) {
                Command command = think();
                triggerEffect(command.effect, command.targets);
            }
        }
    }

    private Command think() {
        Command command;
        try {
            command = bot.think();
        } catch (Exception e) {
            endTurn();
            throw new RuntimeException("Exception occurred during AI turn, remaining turn was skipped. This might cause the client to be out of sync.", e);
        }
        return command;
    }

    public void setBot(Bot bot) {
        this.bot = bot;
    }
}
