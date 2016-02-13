package com.etherblood.cardsmasterserver.matches.internal;

import com.etherblood.cardsmasterserver.matches.internal.players.AbstractPlayer;
import com.etherblood.cardsmasterserver.matches.internal.players.HumanPlayer;
import com.etherblood.cardsmatch.cardgame.MatchState;
import com.etherblood.cardsmatch.cardgame.components.effects.triggers.PlayerActivationTriggerComponent;
import com.etherblood.cardsmatch.cardgame.components.misc.MatchEndedComponent;
import com.etherblood.cardsmatch.cardgame.components.misc.NameComponent;
import com.etherblood.cardsmatch.cardgame.components.misc.OwnerComponent;
import com.etherblood.cardsmatch.cardgame.components.player.ItsMyTurnComponent;
import com.etherblood.cardsmatch.cardgame.components.player.LoserComponent;
import com.etherblood.cardsmatch.cardgame.components.player.WinnerComponent;
import com.etherblood.cardsmatch.cardgame.events.effects.TargetedTriggerEffectEvent;
import com.etherblood.cardsmatch.cardgame.events.endTurn.EndTurnEvent;
import com.etherblood.entitysystem.data.EntityId;
import com.etherblood.entitysystem.filters.FilterQuery;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Philipp
 */
public class MatchWrapper {

    private final FilterQuery currentPlayerQuery = new FilterQuery().setBaseClass(ItsMyTurnComponent.class);
    private final MatchState state;
    private final List<AbstractPlayer> players;
    

    public MatchWrapper(MatchState state, List<AbstractPlayer> players) {
        this.state = state;
        this.players = players;
    }

    public synchronized void triggerEffect(EntityId player, EntityId source, EntityId... targets) {
        if (state.data.has(player, ItsMyTurnComponent.class) && state.data.has(source, PlayerActivationTriggerComponent.class)) {
            OwnerComponent ownerComponent = state.data.get(source, OwnerComponent.class);
            if (ownerComponent != null && ownerComponent.player == player) {
                int checkpoint = getCheckpoint();
                try {
                    state.events.fireEvent(new TargetedTriggerEffectEvent(source, targets));
                    state.events.handleEvents();
                } catch (Exception e) {
                    System.out.println("An exception occurred during handling of effect trigger, match will be rolled back.");
                    rollback(checkpoint);
                    for (AbstractPlayer matchPlayer : players) {
                        if(matchPlayer instanceof HumanPlayer) {
                            ((HumanPlayer)matchPlayer).discardLatestUpdates();
                        }
                    }
                    throw e;
                }
            }
        }
    }
    
    public boolean hasMatchEnded() {
        return !state.data.entities(MatchEndedComponent.class).isEmpty();
    }
    
    public MatchResult getResult() {
        assert hasMatchEnded();
        List<AbstractPlayer> winners = new ArrayList<>();
        List<AbstractPlayer> neutral = new ArrayList<>();
        List<AbstractPlayer> losers = new ArrayList<>();
        for (AbstractPlayer player : players) {
            if(state.data.has(player.getPlayer(), WinnerComponent.class)) {
                winners.add(player);
            } else if(state.data.has(player.getPlayer(), LoserComponent.class)) {
                losers.add(player);
            } else {
                neutral.add(player);
            }
        }
        return new MatchResult(winners, neutral, losers);
    }

    public MatchState getState() {
        return state;
    }
    
    public AbstractPlayer getCurrentPlayer() {
        EntityId currentPlayer = currentPlayerEntity();
        for (AbstractPlayer matchPlayer : players) {
            if(matchPlayer.getPlayer() == currentPlayer) {
                return matchPlayer;
            }
        }
        throw new IllegalStateException();
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
            if(playerClass.isInstance(player)) {
                result.add((T) player);
            }
        }
        return result;
    }
    
    public int getCheckpoint() {
        assert state.events.isEmpty();
        return state.data.getVersion();
    }
    
    public void rollback(int checkpoint) {
        state.data.revertTo(checkpoint);
        state.events.clear();
    }

    private EntityId currentPlayerEntity() {
        EntityId currentPlayer = currentPlayerQuery.first(state.data);
        return currentPlayer;
    }
}
