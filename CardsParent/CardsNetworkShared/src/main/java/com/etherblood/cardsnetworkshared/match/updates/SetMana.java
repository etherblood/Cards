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
public class SetMana extends MatchUpdate {
    private long target;
    private int mana;

    public SetMana() {
    }

    public SetMana(long target, int mana) {
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
