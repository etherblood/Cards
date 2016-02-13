package com.etherblood.cardsnetworkshared.match.updates;

import com.jme3.network.serializing.Serializable;
import com.etherblood.cardsnetworkshared.match.misc.MatchUpdate;

/**
 *
 * @author Philipp
 */
@Serializable
public class GameOver extends MatchUpdate {
    private Long winner;

    public GameOver() {
    }

    public GameOver(Long winner) {
        this.winner = winner;
    }

    public Long getWinner() {
        return winner;
    }
}
