package com.etherblood.cardsmatch.cardgame.bot.monteCarlo;

//package com.etherblood.cardsmatch.cardgame.bot.monteCarlo;
//
//import com.etherblood.cardsmatch.cardgame.bot.commands.CommandManager;
//import com.etherblood.cardsmatch.cardgame.components.misc.MatchEndedComponent;
//import com.etherblood.cardsmatch.cardgame.components.player.ItsMyTurnComponent;
//import com.etherblood.cardsmatch.cardgame.components.player.WinnerComponent;
//import com.etherblood.entitysystem.data.EntityComponentMap;
//import com.etherblood.entitysystem.data.EntityId;
//import com.etherblood.entitysystem.filters.FilterQuery;
//import com.etherblood.entitysystem.version.VersionedEntityComponentMap;
//import com.etherblood.match.MatchContext;
//import com.etherblood.montecarlotreesearch.old.GameSimulation;
//import com.etherblood.montecarlotreesearch.old.MoveIterator;
//import java.util.Set;
//
///**
// *
// * @author Philipp
// */
//public class MatchSimulation implements GameSimulation<VersionedCommand> {
//    
//    private final FilterQuery currentPlayerQuery = new FilterQuery()
//            .setBaseClass(ItsMyTurnComponent.class);
//    
//    private final CommandIterator buffer;
//    private final MatchContext state;
//    private final EntityId player1;
//
//    public MatchSimulation(MatchContext state, EntityId player1) {
//        this.state = state;
//        this.player1 = player1;
//        this.buffer = new CommandIterator(state);
//    }
//    
//    @Override
//    public void makeMove(VersionedCommand move) {
//        move.version = getData().getVersion();
//        state.getBean(CommandManager.class).executeCommand(currentPlayerQuery.first(getData()), move.command);
//    }
//
//    @Override
//    public void undo(VersionedCommand move) {
//        getData().revertTo(move.version);
//    }
//
//    @Override
//    public MoveIterator<VersionedCommand> generateMoves() {
//        buffer.clear();
//        buffer.generate();
//        return buffer;
//    }
//
//    @Override
//    public int getVictoryState() {
//        if(getData().entities(MatchEndedComponent.class).isEmpty()) {
//            return GameSimulation.ONGOING;
//        } else {
//            Set<EntityId> winners = getData().entities(WinnerComponent.class);
//            if(winners.isEmpty()) {
//                return GameSimulation.DRAW;
//            } else {
//                return winners.iterator().next() == player1? 0: 1;
//            }
//        }
//    }
//
//    @Override
//    public int currentPlayer() {
//        return currentPlayerQuery.first(getData()) == player1? 0: 1;
//    }
//    
//    private VersionedEntityComponentMap getData() {
//        return state.getBean(VersionedEntityComponentMap.class);
//    }
//
//}
