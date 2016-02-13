package com.etherblood.cardsnetworkshared.match.updates;

import com.jme3.network.serializing.Serializable;
import com.etherblood.cardsnetworkshared.match.misc.MatchUpdate;

/**
 *
 * @author Philipp
 */
@Serializable
public class AttackUpdate extends MatchUpdate {
    private long attacker, defender;

    public AttackUpdate() {
    }

    public AttackUpdate(long attacker, long defender) {
        this.attacker = attacker;
        this.defender = defender;
    }

    public long getAttacker() {
        return attacker;
    }

    public long getDefender() {
        return defender;
    }
}
