package com.etherblood.cardsnetworkshared.master.commands;

import com.etherblood.cardsnetworkshared.master.updates.BotTo;
import com.jme3.network.serializing.Serializable;

/**
 *
 * @author Philipp
 */
@Serializable
public class MatchRequest {
    private long libraryId;
    private BotTo bot;

    public boolean isVersusBot() {
        return bot != null;
    }

    public long getLibraryId() {
        return libraryId;
    }

    public BotTo getBot() {
        return bot;
    }

    public void setLibraryId(long libraryId) {
        this.libraryId = libraryId;
    }

    public void setBot(BotTo bot) {
        this.bot= bot;
    }
}
