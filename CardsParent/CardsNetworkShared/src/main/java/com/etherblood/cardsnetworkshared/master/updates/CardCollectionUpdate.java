package com.etherblood.cardsnetworkshared.master.updates;

import com.etherblood.cardsnetworkshared.master.misc.LobbyUpdate;
import com.jme3.network.serializing.Serializable;

/**
 *
 * @author Philipp
 */
@Serializable
public class CardCollectionUpdate extends LobbyUpdate {
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
