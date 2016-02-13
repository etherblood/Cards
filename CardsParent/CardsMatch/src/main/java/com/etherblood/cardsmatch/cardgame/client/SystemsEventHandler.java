/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.etherblood.cardsmatch.cardgame.client;

import com.etherblood.eventsystem.GameEvent;

/**
 *
 * @author Philipp
 */
public interface SystemsEventHandler {
    void onEvent(Class systemClass, GameEvent gameEvent);
    void setEnabled(boolean value);
    public boolean isEnabled();
}
