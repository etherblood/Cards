package com.etherblood.cardsnetworkshared.master.updates;

import com.jme3.network.serializing.Serializable;
import java.util.List;

/**
 *
 * @author Philipp
 */
@Serializable
public class AvailableBotsUpdate {
    public List<BotTo> bots;
}
