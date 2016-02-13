/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.etherblood.cardsmatch.cardgame.client;

import com.etherblood.eventsystem.GameEvent;
import java.util.ArrayList;


public class MultiSystemsEventHandler implements SystemsEventHandler {
    private final ArrayList<SystemsEventHandler> handlers = new ArrayList<>();
    private boolean enabled = true;
    
    @Override
    public void onEvent(Class systemClass, GameEvent gameEvent) {
        assert enabled;
        for (SystemsEventHandler handler : handlers) {
            if(handler.isEnabled()) {
                handler.onEvent(systemClass, gameEvent);
            }
        }
    }

    public ArrayList<SystemsEventHandler> getHandlers() {
        return handlers;
    }

    @Override
    public boolean isEnabled() {
        return enabled;
    }

    @Override
    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }
    
}
