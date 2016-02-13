/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.etherblood.cardsnetworkshared.match.updates;

import com.jme3.network.serializing.Serializable;
import com.etherblood.cardsnetworkshared.match.misc.MatchUpdate;

/**
 *
 * @author Philipp
 */
@Serializable
public class SetHealth extends MatchUpdate {
    private long target;
    private int health;

    public SetHealth() {
    }

    public SetHealth(long target, int health) {
        this.target = target;
        this.health = health;
    }

    public long getTarget() {
        return target;
    }

    public int getHealth() {
        return health;
    }
}
