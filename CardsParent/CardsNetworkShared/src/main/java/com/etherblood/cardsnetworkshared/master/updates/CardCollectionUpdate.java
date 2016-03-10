package com.etherblood.cardsnetworkshared.master.updates;

import com.jme3.network.serializing.Serializable;

/**
 *
 * @author Philipp
 */
@Serializable
public class CardCollectionUpdate {
    private String[] cards;

    public CardCollectionUpdate() {
    }

    public CardCollectionUpdate(String[] cards) {
        this.cards = cards;
    }

    public String[] getCards() {
        return cards;
    }
}
