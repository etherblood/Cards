package com.etherblood.cardsmasterserver.matches.internal.players;

import com.etherblood.cardsmasterserver.matches.internal.MatchContextWrapper;
import com.etherblood.entitysystem.data.EntityId;

/**
 *
 * @author Philipp
 */
public abstract class AbstractPlayer {
    
    private MatchContextWrapper match;
    private final EntityId player;

    public AbstractPlayer(EntityId player) {
        this.player = player;
    }
    
    public void triggerEffect(EntityId source, EntityId... targets) {
        match.triggerEffect(player, source, targets);
    }
    
    public MatchContextWrapper getMatch() {
        return match;
    }

    public EntityId getPlayer() {
        assert player != null;
        return player;
    }
    
//    protected void endTurn() {
//        match.getEvents().fireEvent(new EndTurnEvent(player));
//        match.getEvents().handleEvents();
//    }

    public void setMatch(MatchContextWrapper match) {
        this.match = match;
    }
    
    public abstract void clearCache();
}
