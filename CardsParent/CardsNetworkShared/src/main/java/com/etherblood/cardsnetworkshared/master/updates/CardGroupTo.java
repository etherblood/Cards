package com.etherblood.cardsnetworkshared.master.updates;

import com.jme3.network.serializing.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 *
 * @author Philipp
 */
@Serializable
public class CardGroupTo {
    public String displayName;
    public long id;
    public Set<CardsTo> cards = new HashSet<>();

    @Override
    public String toString() {
        return displayName;
    }
}
