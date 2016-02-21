package com.etherblood.cardsmatch.cardgame;

import com.etherblood.cardsmatch.cardgame.client.SystemsEventHandler;
import com.etherblood.eventsystem.GameEvent;
import java.util.ArrayList;

/**
 *
 * @author Philipp
 */
public class EventLogs implements SystemsEventHandler {
    private final ArrayList<GameEvent> events = new ArrayList<>();
    private final ArrayList<Class> systems = new ArrayList<>();
    private boolean enabled = true;

    @Override
    public void onEvent(Class systemClass, GameEvent gameEvent) {
        assert enabled;
        systems.add(systemClass);
        events.add(gameEvent);
//            System.out.println(gameEvent.getClass().getSimpleName() + " => " + systemClass.getSimpleName());
    }
    
    public int size() {
        return  events.size();
    }
    
    public GameEvent event(int index) {
        return events.get(index);
    }
    
    public Class system(int index) {
        return systems.get(index);
    }
}
