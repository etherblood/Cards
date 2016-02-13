/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.etherblood.cardsmasterserver.matches.internal.players;

import com.etherblood.cardsmasterserver.matches.internal.MatchWrapper;
import com.etherblood.cardsmatch.cardgame.events.endTurn.EndTurnEvent;
import com.etherblood.entitysystem.data.EntityId;

/**
 *
 * @author Philipp
 */
public abstract class AbstractPlayer {
    
    private final MatchWrapper match;
    private final EntityId player;

    public AbstractPlayer(MatchWrapper match, EntityId player) {
        this.match = match;
        this.player = player;
    }
    
    public void triggerEffect(EntityId source, EntityId... targets) {
        match.triggerEffect(player, source, targets);
    }
    
    public MatchWrapper getMatch() {
        return match;
    }

    public EntityId getPlayer() {
        return player;
    }
    
    protected void endTurn() {
        match.getState().events.fireEvent(new EndTurnEvent(player));
        match.getState().events.handleEvents();
    }
}
