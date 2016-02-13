package com.etherblood.cardsnetworkshared.match.updates;

import com.jme3.network.serializing.Serializable;
import com.etherblood.cardsnetworkshared.match.misc.MatchUpdate;

/**
 *
 * @author Philipp
 */
@Serializable
public class SetOwner extends MatchUpdate {
    private long target, owner;

    public SetOwner() {
    }

    public SetOwner(long target, long owner) {
        this.target = target;
        this.owner = owner;
    }

    public long getTarget() {
        return target;
    }

    public long getOwner() {
        return owner;
    }
}
