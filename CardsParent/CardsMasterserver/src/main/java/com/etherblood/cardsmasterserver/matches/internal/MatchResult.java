package com.etherblood.cardsmasterserver.matches.internal;

import com.etherblood.cardsmasterserver.matches.internal.players.AbstractPlayer;
import java.util.List;

/**
 *
 * @author Philipp
 */
public class MatchResult {
    private final List<AbstractPlayer> winners;
    private final List<AbstractPlayer> neutral;
    private final List<AbstractPlayer> losers;

    public MatchResult(List<AbstractPlayer> winners, List<AbstractPlayer> neutral, List<AbstractPlayer> losers) {
        this.winners = winners;
        this.neutral = neutral;
        this.losers = losers;
    }

    public List<AbstractPlayer> getWinners() {
        return winners;
    }

    public List<AbstractPlayer> getNeutral() {
        return neutral;
    }

    public List<AbstractPlayer> getLosers() {
        return losers;
    }
}
