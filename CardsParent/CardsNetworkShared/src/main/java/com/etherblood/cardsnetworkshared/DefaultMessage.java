package com.etherblood.cardsnetworkshared;

import com.jme3.network.AbstractMessage;
import com.jme3.network.serializing.Serializable;

/**
 *
 * @author Philipp
 */
@Serializable
public class DefaultMessage<T> extends AbstractMessage {
    private T data;

    public DefaultMessage(T data) {
        super(true);
        this.data = data;
    }
    public DefaultMessage() {
        super(true);
    }

    public T getData() {
        return data;
    }
}
