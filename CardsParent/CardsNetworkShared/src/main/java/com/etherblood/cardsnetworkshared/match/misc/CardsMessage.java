package com.etherblood.cardsnetworkshared.match.misc;

import com.jme3.network.AbstractMessage;
import com.jme3.network.serializing.Serializable;

/**
 *
 * @author Philipp
 */
@Serializable
public class CardsMessage<T> extends AbstractMessage {
    private T data;

    public CardsMessage(T data) {
        super(true);
        this.data = data;
    }
    public CardsMessage() {
        super(true);
    }

    public T getData() {
        return data;
    }
}
