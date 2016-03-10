package com.etherblood.cardsnetworkshared.master.commands;

import com.jme3.network.serializing.Serializable;

/**
 *
 * @author Philipp
 */
@Serializable
public class MatchRequest {
    private boolean versusBot;

    public MatchRequest() {
    }

    public MatchRequest(boolean versusBot) {
        this.versusBot = versusBot;
    }

    public boolean isVersusBot() {
        return versusBot;
    }
}
