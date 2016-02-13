package com.etherblood.cardsnetworkshared.match.updates;

import com.jme3.network.serializing.Serializable;
import com.etherblood.cardsnetworkshared.match.misc.MatchUpdate;

/**
 *
 * @author Philipp
 */
@Serializable
public class CreateEntity extends MatchUpdate {
    private long card;
    private String name;

    public CreateEntity() {
    }

    public CreateEntity(long card, String name) {
        this.card = card;
        this.name = name;
    }

    public long getCard() {
        return card;
    }

    public String getName() {
        return name;
    }
}
