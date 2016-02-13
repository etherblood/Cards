package com.etherblood.cardsmatch.cardgame.bot.monteCarlo;

import com.etherblood.cardsmatch.cardgame.MatchState;
import com.etherblood.cardsmatch.cardgame.bot.Bot;
import com.etherblood.cardsmatch.cardgame.bot.commands.Command;
import com.etherblood.entitysystem.data.EntityId;
import com.etherblood.montecarlotreesearch.MonteCarloAgent;
import com.etherblood.montecarlotreesearch.MonteCarloNode;

/**
 *
 * @author Philipp
 */
public class MonteCarloBot implements Bot {

    private final MonteCarloAgent<VersionedCommand> agent;
    private long timeoutMillis = 500;

    public MonteCarloBot(MatchState state, EntityId player1) {
        agent = new MonteCarloAgent<VersionedCommand>(new MatchSimulation(state, player1));
    }
    
    @Override
    public Command think() {
        return agent.calcBestMove(new MonteCarloNode(), timeoutMillis).command;//TODO reuse old node
    }

    public void setVerbose(boolean value) {
        agent.setVerbose(value);
    }

    public void setTimeoutMillis(long timeoutMillis) {
        this.timeoutMillis = timeoutMillis;
    }
    
}
