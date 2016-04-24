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
    
    public boolean hasMatchEnded() {
        return matchState.isGameOver();
    }

    public AbstractPlayer getCurrentPlayer() {
        PlayerProxy currentProxy = matchState.getCurrentPlayer();
        for (AbstractPlayer player : players) {
            if(player.getProxy() == currentProxy) {
                return player;
            }
        }
        throw new IllegalStateException(currentProxy.toString());
    }

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

    public Logger getLogger() {
        return logger;
    }

    public UUID getUuid() {
        return uuid;
    }
}
