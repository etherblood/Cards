package com.etherblood.cardsnetworkshared.match.updates;

import com.jme3.network.serializing.Serializable;
import com.etherblood.cardsnetworkshared.match.misc.MatchUpdate;

/**
 *
 * @author Philipp
 */
@Serializable
public class SetZone extends MatchUpdate {
    private long target;
    private int zone;

    public SetZone() {
    }

    public SetZone(long target, int zone) {
        this.target = target;
        this.zone = zone;
    }

    public long getTarget() {
        return target;
    }

    public int getZone() {
        return zone;
    }
}
