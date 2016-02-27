package com.etherblood.cardsnetworkshared;

import com.jme3.network.AbstractMessage;
import com.jme3.network.serializing.Serializable;

/**
 *
 * @author Philipp
 */
@Serializable
public class EncryptedMessage extends AbstractMessage {
    private byte[] data;
    
    public EncryptedMessage(byte[] data) {
        super(true);
        this.data = data;
    }

    public EncryptedMessage() {
        super(true);
    }

    public byte[] getData() {
        return data;
    }
    
}
