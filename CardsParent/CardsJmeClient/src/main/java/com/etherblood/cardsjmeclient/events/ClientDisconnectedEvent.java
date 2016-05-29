package com.etherblood.cardsjmeclient.events;

import com.jme3.network.ClientStateListener;

/**
 *
 * @author Philipp
 */
public class ClientDisconnectedEvent {
    private final ClientStateListener.DisconnectInfo info;
    public ClientDisconnectedEvent(ClientStateListener.DisconnectInfo info) {
        this.info = info;
    }

    public ClientStateListener.DisconnectInfo getInfo() {
        return info;
    }

}
