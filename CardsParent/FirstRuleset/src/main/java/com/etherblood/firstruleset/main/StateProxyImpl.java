package com.etherblood.firstruleset.main;

import com.etherblood.cardscontext.MatchContext;
import com.etherblood.cardsmatch.cardgame.components.misc.MatchEndedComponent;
import com.etherblood.cardsmatch.cardgame.components.player.ItsMyTurnComponent;
import com.etherblood.cardsmatch.cardgame.components.player.LoserComponent;
import com.etherblood.cardsmatch.cardgame.components.player.WinnerComponent;
import com.etherblood.cardsmatchapi.PlayerProxy;
import com.etherblood.cardsmatchapi.PlayerResult;
import com.etherblood.cardsmatchapi.StateProxy;
import com.etherblood.entitysystem.data.EntityId;
import com.etherblood.entitysystem.filters.FilterQuery;
import com.etherblood.entitysystem.version.VersionedEntityComponentMap;
import java.util.List;

/**
 *
 * @author Philipp
 */
public class StateProxyImpl implements StateProxy {
    private final FilterQuery currentPlayerQuery = new FilterQuery().setBaseClass(ItsMyTurnComponent.class);
    private final MatchContext context;
    private final List<AbstractPlayerProxy> players;

    public StateProxyImpl(MatchContext context, List<AbstractPlayerProxy> players) {
        this.context = context;
        this.players = players;
    }
    
    @Override
    public boolean isGameOver() {
        return !getData().entities(MatchEndedComponent.class).isEmpty();
    }

    @Override
    public PlayerProxy getCurrentPlayer() {
        EntityId currentPlayer = currentPlayerEntity();
        assert currentPlayer != null;
        for (AbstractPlayerProxy matchPlayer : players) {
            if (matchPlayer.getEntity().equals(currentPlayer)) {
                return matchPlayer;
            }
        }
        throw new IllegalStateException(currentPlayer.toString());
    }

    @Override
    public PlayerResult getResult(PlayerProxy player) {
        AbstractPlayerProxy proxy = (AbstractPlayerProxy) player;
        if (getData().has(proxy.getEntity(), WinnerComponent.class)) {
                return PlayerResult.VICTOR;
            } else if (getData().has(proxy.getEntity(), LoserComponent.class)) {
                return PlayerResult.LOSER;
            } else if(isGameOver()) {
                return PlayerResult.DRAW;
            }
        return PlayerResult.NONE;
    }

    private EntityId currentPlayerEntity() {
        return currentPlayerQuery.first(getData());
    }

    public VersionedEntityComponentMap getData() {
        return context.getBean(VersionedEntityComponentMap.class);
    }
    
}
