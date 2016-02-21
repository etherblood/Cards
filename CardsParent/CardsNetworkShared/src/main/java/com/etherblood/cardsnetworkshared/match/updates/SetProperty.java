package com.etherblood.cardsnetworkshared.match.updates;

import com.etherblood.cardsnetworkshared.match.misc.MatchUpdate;
import com.jme3.network.serializing.Serializable;

/**
 *
 * @author Philipp
 */
@Serializable
public class SetProperty extends MatchUpdate {
    private long card;
    private String key;
    private int value;

    public SetProperty() {
    }

    public SetProperty(long card, String key, int value) {
        this.card = card;
        this.key = key;
        this.value = value;
    }

    public long getCard() {
        return card;
    }

    public String getKey() {
        return key;
    }

    public int getValue() {
        return value;
    }
}
