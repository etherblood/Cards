package com.etherblood.cardsmatch.cardgame.bot.monteCarlo;

import com.etherblood.cardsmatch.cardgame.MatchState;
import com.etherblood.cardsmatch.cardgame.bot.commands.CommandManager;
import com.etherblood.cardsmatch.cardgame.components.misc.MatchEndedComponent;
import com.etherblood.cardsmatch.cardgame.components.player.ItsMyTurnComponent;
import com.etherblood.cardsmatch.cardgame.components.player.WinnerComponent;
import com.etherblood.entitysystem.data.EntityId;
import com.etherblood.entitysystem.filters.FilterQuery;
import com.etherblood.montecarlotreesearch.GameSimulation;
import com.etherblood.montecarlotreesearch.MoveIterator;
import java.util.Set;

/**
 *
 * @author Philipp
 */
public class MatchSimulation implements GameSimulation<VersionedCommand> {
    
    private final FilterQuery currentPlayerQuery = new FilterQuery()
            .setBaseClass(ItsMyTurnComponent.class);
    
    private final CommandIterator buffer;
    private final MatchState state;
    private final EntityId player1;

    public MatchSimulation(MatchState state, EntityId player1) {
        this.state = state;
        this.player1 = player1;
        this.buffer = new CommandIterator(state);
    }
    
    @Override
    public void makeMove(VersionedCommand move) {
        move.version = state.data.getVersion();
        state.getContext().getBean(CommandManager.class).executeCommand(currentPlayerQuery.first(state.data), move.command);
    }

    @Override
    public void undo(VersionedCommand move) {
        state.data.revertTo(move.version);
    }

    @Override
    public MoveIterator<VersionedCommand> generateMoves() {
        buffer.clear();
        buffer.generate();
        return buffer;
    }

    @Override
    public int getVictoryState() {
        if(state.data.entities(MatchEndedComponent.class).isEmpty()) {
            return GameSimulation.ONGOING;
        } else {
            Set<EntityId> winners = state.data.entities(WinnerComponent.class);
            if(winners.isEmpty()) {
                return GameSimulation.DRAW;
            } else {
                return winners.iterator().next() == player1? 0: 1;
            }
        }
    }

    @Override
    public int currentPlayer() {
        return currentPlayerQuery.first(state.data) == player1? 0: 1;
    }

}
