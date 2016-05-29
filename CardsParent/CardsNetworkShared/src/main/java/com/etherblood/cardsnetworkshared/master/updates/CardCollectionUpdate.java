package com.etherblood.cardsnetworkshared.master.updates;

import com.jme3.network.serializing.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 *
 * @author Philipp
 */
@Serializable
public class CardCollectionUpdate {
    public CardGroupTo collection;
    public Set<CardGroupTo> libraries = new HashSet<>();
}
