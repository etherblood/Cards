package com.etherblood.cardsmasterserver.matches.internal.players;

import com.etherblood.cardsmasterserver.matches.internal.MatchWrapper;
import com.etherblood.cardsmatch.cardgame.bot.Bot;
import com.etherblood.cardsmatch.cardgame.bot.commands.Command;
import com.etherblood.cardsmatch.cardgame.components.player.ItsMyTurnComponent;
import com.etherblood.entitysystem.data.EntityId;

/**
 *
 * @author Philipp
 */
public class AiPlayer extends AbstractPlayer {

    private final Bot bot;

    public AiPlayer(MatchWrapper match, EntityId player, Bot bot) {
        super(match, player);
        this.bot = bot;
    }

    public void compute() {
        MatchWrapper matchWrapper = getMatch();
        synchronized (matchWrapper) {
            if (!matchWrapper.hasMatchEnded() && matchWrapper.getState().data.has(getPlayer(), ItsMyTurnComponent.class)) {
                Command command = think();
                triggerEffect(command.effect, command.targets);
            }
        }
    }

    private Command think() {
        MatchWrapper matchWrapper = getMatch();
        int checkpoint = matchWrapper.getCheckpoint();
        matchWrapper.getState().setLoggingEnabled(false);
        Command command;
        try {
            assert matchWrapper.getState().events.isEmpty();
            command = bot.think();
            assert matchWrapper.getState().events.isEmpty();
        } catch (Exception e) {
            matchWrapper.rollback(checkpoint);
            endTurn();
            throw new RuntimeException("Exception occurred during AI turn, remaining turn was skipped. This might cause the client to be out of sync.", e);
        } finally {
            matchWrapper.getState().setLoggingEnabled(true);
        }
        return command;
    }
}
