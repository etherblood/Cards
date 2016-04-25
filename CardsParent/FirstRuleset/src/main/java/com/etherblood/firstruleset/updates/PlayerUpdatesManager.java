package com.etherblood.firstruleset.updates;

import com.etherblood.cardsmatch.cardgame.GlobalEventHandler;
import com.etherblood.eventsystem.GameEvent;
import com.etherblood.eventsystem.GameEventHandler;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 * @author Philipp
 */
public class PlayerUpdatesManager implements GlobalEventHandler {

    private final Map<Class, UpdateBuilder> builders = new HashMap<>();
    private final List<VisibilityMatchUpdate> updates = new ArrayList<>();
    
    @Override
    public <T extends GameEvent> void onEvent(Class<GameEventHandler<T>> systemClass, T gameEvent) {
        UpdateBuilder builder = builders.get(systemClass);
        if(builder != null) {
            builder.fromEvent(gameEvent, updates);
        }
    }

    public List<VisibilityMatchUpdate> getUpdates() {
        return updates;
    }

}
