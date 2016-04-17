package com.etherblood.cardsmasterserver.matches.internal;

import com.etherblood.cardsmasterserver.matches.internal.players.AbstractPlayer;
import com.etherblood.cardsmatch.cardgame.CommandHandler;
import com.etherblood.cardsmatch.cardgame.IllegalCommandException;
import com.etherblood.cardsmatch.cardgame.UpdateBuilder;
import com.etherblood.cardsmatch.cardgame.components.misc.MatchEndedComponent;
import com.etherblood.cardsmatch.cardgame.components.player.ItsMyTurnComponent;
import com.etherblood.cardsmatch.cardgame.components.player.LoserComponent;
import com.etherblood.cardsmatch.cardgame.components.player.WinnerComponent;
import com.etherblood.entitysystem.data.EntityId;
import com.etherblood.entitysystem.filters.FilterQuery;
import com.etherblood.entitysystem.version.VersionedEntityComponentMap;
import com.etherblood.cardscontext.MatchContext;
import com.etherblood.eventsystem.GameEventQueueImpl;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 *
 * @author Philipp
 */
public class MatchContextWrapper {

    private final FilterQuery currentPlayerQuery = new FilterQuery().setBaseClass(ItsMyTurnComponent.class);
    private MatchContext state;
    private List<AbstractPlayer> players;
    private Map<Class, UpdateBuilder> updateBuilders;

    public void init(MatchContext context, Map<Class, UpdateBuilder> updateBuilders, List<AbstractPlayer> players) {
        this.state = context;
        this.updateBuilders = updateBuilders;
        this.players = players;
        for (AbstractPlayer player : players) {
            player.setMatch(this);
        }
    }

    public synchronized void triggerEffect(EntityId player, EntityId source, EntityId... targets) {
        try {
            getCommandHandler().handleCommand(player, source, targets);
        } catch (IllegalCommandException e) {
            for (AbstractPlayer matchPlayer : players) {
                matchPlayer.clearCache();
            }
            throw e;
        }
    }

    public boolean hasMatchEnded() {
        return !getData().entities(MatchEndedComponent.class).isEmpty();
    }

    public MatchResult getResult() {
        assert hasMatchEnded();
        List<AbstractPlayer> winners = new ArrayList<>();
        List<AbstractPlayer> neutral = new ArrayList<>();
        List<AbstractPlayer> losers = new ArrayList<>();
        for (AbstractPlayer player : players) {
            if (getData().has(player.getPlayer(), WinnerComponent.class)) {
                winners.add(player);
            } else if (getData().has(player.getPlayer(), LoserComponent.class)) {
                losers.add(player);
            } else {
                neutral.add(player);
            }
        }
        return new MatchResult(winners, neutral, losers);
    }

    public MatchContext getContext() {
        return state;
    }

    public AbstractPlayer getCurrentPlayer() {
        EntityId currentPlayer = currentPlayerEntity();
        assert currentPlayer != null;
        for (AbstractPlayer matchPlayer : players) {
            if (matchPlayer.getPlayer().equals(currentPlayer)) {
                return matchPlayer;
            }
        }
        throw new IllegalStateException(currentPlayer.toString());
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

    private EntityId currentPlayerEntity() {
        return currentPlayerQuery.first(getData());
    }

    public GameEventQueueImpl getEvents() {
        return state.getBean(GameEventQueueImpl.class);
    }
    
    private CommandHandler getCommandHandler() {
        return state.getBean(CommandHandler.class);
    }

    public VersionedEntityComponentMap getData() {
        return state.getBean(VersionedEntityComponentMap.class);
    }

    public Map<Class, UpdateBuilder> getUpdateBuilders() {
        return updateBuilders;
    }
}
