package com.etherblood.cardsjmeclient.events;

import com.etherblood.cardsjmeclient.ScreenKeys;

/**
 *
 * @author Philipp
 */
public class ScreenRequestEvent {
    public final ScreenKeys key;

    public ScreenRequestEvent(ScreenKeys key) {
        this.key = key;
    }
}
