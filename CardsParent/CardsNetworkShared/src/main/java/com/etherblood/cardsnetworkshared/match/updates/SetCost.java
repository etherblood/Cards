package com.etherblood.cardsnetworkshared.match.updates;

import com.jme3.network.serializing.Serializable;
import com.etherblood.cardsnetworkshared.match.misc.MatchUpdate;

/**
 *
 * @author Philipp
 */
@Serializable
public class SetCost extends MatchUpdate {
    private long target;
    private int mana;

    public SetCost() {
    }

    public SetCost(long target, int mana) {
        this.target = target;
        this.mana = mana;
    }

    public long getTarget() {
        return target;
    }

    public int getMana() {
        return mana;
    }
}
