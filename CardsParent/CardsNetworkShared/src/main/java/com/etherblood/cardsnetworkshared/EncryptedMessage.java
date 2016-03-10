package com.etherblood.cardsnetworkshared;

import com.jme3.network.AbstractMessage;
import com.jme3.network.Message;
import com.jme3.network.serializing.Serializable;

/**
 *
 * @author Philipp
 */
@Serializable
public class EncryptedMessage extends AbstractMessage {

    private Message message;

    public EncryptedMessage() {
        super(true);
    }

    public EncryptedMessage(Message message) {
        super(true);
        this.message = message;
    }

    public Message getMessage() {
        return message;
    }

    public void setMessage(Message message) {
        this.message = message;
    }

}
