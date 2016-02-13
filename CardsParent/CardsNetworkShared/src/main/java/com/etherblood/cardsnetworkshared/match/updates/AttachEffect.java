package com.etherblood.cardsnetworkshared.match.updates;

import com.jme3.network.serializing.Serializable;
import com.etherblood.cardsnetworkshared.match.misc.MatchUpdate;

/**
 *
 * @author Philipp
 */
@Serializable
public class AttachEffect extends MatchUpdate {
    private long card, effect;
    private String name;

    public AttachEffect() {
    }

    public AttachEffect(long card, long effect, String name) {
        this.card = card;
        this.effect = effect;
        this.name = name;
    }

    public long getCard() {
        return card;
    }

    public long getEffect() {
        return effect;
    }

    public String getName() {
        return name;
    }

}
