package com.etherblood.cardsnetworkshared.master.updates;

import com.jme3.network.serializing.Serializable;

/**
 *
 * @author Philipp
 */
@Serializable
public class BotTo {
    public CardGroupTo library;
    public String displayName;

    @Override
    public String toString() {
        return displayName;
    }
}
