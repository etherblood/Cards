package com.etherblood.cardsmasterserver.matches.internal;

import com.etherblood.logging.Logger;
import com.etherblood.cardsmasterserver.matches.internal.players.AbstractPlayer;
import com.etherblood.cardsmatchapi.PlayerProxy;
import com.etherblood.cardsmatchapi.PlayerResult;
import com.etherblood.cardsmatchapi.StateProxy;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 *
 * @author Philipp
 */
public class MatchContextWrapper {
    private final UUID uuid;
    private StateProxy matchState;
    private List<AbstractPlayer> players;
    private final Logger logger;
//    private final FilterQuery currentPlayerQuery = new FilterQuery().setBaseClass(ItsMyTurnComponent.class);
//    private MatchContext state;
//    private List<AbstractPlayer> players;

    public MatchContextWrapper(UUID uuid, Logger logger) {
        this.uuid = uuid;
        this.logger = logger;
    }

    public void init(StateProxy matchState, List<AbstractPlayer> players) {
        this.matchState = matchState;
        this.players = players;
        for (AbstractPlayer player : players) {
            player.setMatch(this);
        }
    }
//
//    public synchronized void triggerEffect(EntityId player, EntityId source, EntityId... targets) {
//        try {
//            getCommandHandler().handleCommand(player, source, targets);
//        } catch (IllegalCommandException e) {
//            for (AbstractPlayer matchPlayer : players) {
//                matchPlayer.clearCache();
//            }
//            throw e;
//        }
//    }

    public boolean hasMatchEnded() {
        return matchState.isGameOver();
//        return !getData().entities(MatchEndedComponent.class).isEmpty();
    }

//    public MatchResult getResult() {
//        assert hasMatchEnded();
//        List<AbstractPlayer> winners = new ArrayList<>();
//        List<AbstractPlayer> neutral = new ArrayList<>();
//        List<AbstractPlayer> losers = new ArrayList<>();
//        for (AbstractPlayer player : players) {
//            if (getData().has(player.getPlayer(), WinnerComponent.class)) {
//                winners.add(player);
//            } else if (getData().has(player.getPlayer(), LoserComponent.class)) {
//                losers.add(player);
//            } else {
//                neutral.add(player);
//            }
//        }
//        return new MatchResult(winners, neutral, losers);
//    }

//    public MatchContext getContext() {
//        return state;
//    }

    public AbstractPlayer getCurrentPlayer() {
        PlayerProxy currentProxy = matchState.getCurrentPlayer();
        for (AbstractPlayer player : players) {
            if(player.getProxy() == currentProxy) {
                return player;
            }
        }
        throw new IllegalStateException(currentProxy.toString());
    }

//    public void endCurrentTurn() {
//        state.events.fireEvent(new EndTurnEvent(currentPlayerEntity()));
//        state.events.handleEvents();
//    }
    public List<AbstractPlayer> getPlayers() {
        return players;
    }

    public <T extends AbstractPlayer> List<T> getPlayers(Class<T> playerClass) {
        ArrayList<T> result = new ArrayList<>();
        for (AbstractPlayer player : players) {
            if (playerClass.isInstance(player)) {
                result.add((T) player);
            }
        }
        return result;
    }
    
    public PlayerResult getResult(AbstractPlayer player) {
        return matchState.getResult(player.getProxy());
    }

//    private EntityId currentPlayerEntity() {
//        return currentPlayerQuery.first(getData());
//    }
//
//    public GameEventQueueImpl getEvents() {
//        return state.getBean(GameEventQueueImpl.class);
//    }
//    
//    private CommandHandler getCommandHandler() {
//        return state.getBean(CommandHandler.class);
//    }
//
//    public VersionedEntityComponentMap getData() {
//        return state.getBean(VersionedEntityComponentMap.class);
//    }

    public Logger getLogger() {
        return logger;
    }

    public UUID getUuid() {
        return uuid;
    }
}
