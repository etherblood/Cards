package com.etherblood.cardsmasterserver.matches.internal;

import com.etherblood.cardsmasterserver.matches.internal.players.AbstractPlayer;
import com.etherblood.cardsmasterserver.matches.internal.players.AiPlayer;
import com.etherblood.cardsmasterserver.matches.internal.players.HumanPlayer;
import com.etherblood.cardsmatch.cardgame.components.effects.triggers.PlayerActivationTriggerComponent;
import com.etherblood.cardsmatch.cardgame.components.misc.MatchEndedComponent;
import com.etherblood.cardsmatch.cardgame.components.misc.OwnerComponent;
import com.etherblood.cardsmatch.cardgame.components.player.ItsMyTurnComponent;
import com.etherblood.cardsmatch.cardgame.components.player.LoserComponent;
import com.etherblood.cardsmatch.cardgame.components.player.WinnerComponent;
import com.etherblood.cardsmatch.cardgame.events.effects.TargetedTriggerEffectEvent;
import com.etherblood.entitysystem.data.EntityComponentMap;
import com.etherblood.entitysystem.data.EntityId;
import com.etherblood.entitysystem.filters.FilterQuery;
import com.etherblood.entitysystem.version.VersionedEntityComponentMap;
import com.etherblood.eventsystem.GameEventQueueImpl;
import com.etherblood.match.MatchContext;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Philipp
 */
public class MatchContextWrapper {

    private final FilterQuery currentPlayerQuery = new FilterQuery().setBaseClass(ItsMyTurnComponent.class);
    private MatchContext state;
    private List<AbstractPlayer> players;
    
    public void init(MatchContext context, List<AbstractPlayer> players) {
        this.state = context;
        this.players = players;
        for (AbstractPlayer player : players) {
            player.setMatch(this);
        }
    }

    public synchronized void triggerEffect(EntityId player, EntityId source, EntityId... targets) {
        if (getData().has(player, ItsMyTurnComponent.class) && getData().has(source, PlayerActivationTriggerComponent.class)) {
            OwnerComponent ownerComponent = getData().get(source, OwnerComponent.class);
            if (ownerComponent != null && ownerComponent.player.equals(player)) {
                int checkpoint = getData().getVersion();
                try {
                    getEvents().fireEvent(new TargetedTriggerEffectEvent(source, targets));
                    getEvents().handleEvents();
                } catch (Exception e) {
                    System.out.println("An exception occurred during handling of effect trigger, match will be rolled back.");
                    getData().revertTo(checkpoint);
                    for (AbstractPlayer matchPlayer : players) {
                        if(matchPlayer instanceof HumanPlayer) {
                            ((HumanPlayer)matchPlayer).discardLatestUpdates();
                        } else if(matchPlayer instanceof AiPlayer) {
                            ((AiPlayer)matchPlayer).clearCache();
                        }
                    }
                    throw e;
                }
            }
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
            if(getData().has(player.getPlayer(), WinnerComponent.class)) {
                winners.add(player);
            } else if(getData().has(player.getPlayer(), LoserComponent.class)) {
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
            if(matchPlayer.getPlayer().equals(currentPlayer)) {
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
            if(playerClass.isInstance(player)) {
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
    
    public VersionedEntityComponentMap getData() {
        return state.getBean(VersionedEntityComponentMap.class);
    }
}
