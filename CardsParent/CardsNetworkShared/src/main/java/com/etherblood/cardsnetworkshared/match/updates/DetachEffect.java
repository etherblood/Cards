package com.etherblood.cardsnetworkshared.match.updates;

import com.jme3.network.serializing.Serializable;
import com.etherblood.cardsnetworkshared.match.misc.MatchUpdate;

/**
 *
 * @author Philipp
 */
@Serializable
public class DetachEffect extends MatchUpdate {
    private long effect;

    public DetachEffect() {
    }

    public DetachEffect(long effect) {
        this.effect = effect;
    }

    public long getEffect() {
        return effect;
    }

}
