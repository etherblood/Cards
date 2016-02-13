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
public class SetAttack extends MatchUpdate {
    private long target;
    private int attack;

    public SetAttack() {
    }

    public SetAttack(long target, int attack) {
        this.target = target;
        this.attack = attack;
    }

    public long getTarget() {
        return target;
    }

    public int getAttack() {
        return attack;
    }
    
}
