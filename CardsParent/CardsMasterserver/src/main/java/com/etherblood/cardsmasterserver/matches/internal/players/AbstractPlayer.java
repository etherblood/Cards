package com.etherblood.cardsmasterserver.matches.internal.players;

import com.etherblood.cardsmasterserver.matches.internal.MatchContextWrapper;
import com.etherblood.cardsmatchapi.PlayerProxy;

/**
 *
 * @author Philipp
 */
public abstract class AbstractPlayer {
    
    private MatchContextWrapper match;
    
    public MatchContextWrapper getMatch() {
        return match;
    }

    public void setMatch(MatchContextWrapper match) {
        this.match = match;
    }
    
    public abstract void clearCache();
    
    public abstract PlayerProxy getProxy();
}
