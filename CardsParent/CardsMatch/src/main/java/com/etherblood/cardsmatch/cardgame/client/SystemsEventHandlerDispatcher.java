package com.etherblood.cardsmatch.cardgame.client;

import com.etherblood.eventsystem.GameEvent;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Philipp
 */
public class SystemsEventHandlerDispatcher implements SystemsEventHandler {
    private final List<SystemsEventHandler> handlers = new ArrayList<>();

    public List<SystemsEventHandler> getHandlers() {
        return handlers;
    }
    
    @Override
    public void onEvent(Class systemClass, GameEvent gameEvent) {
        for (SystemsEventHandler player : handlers) {
            player.onEvent(systemClass, gameEvent);
        }
    }

}
