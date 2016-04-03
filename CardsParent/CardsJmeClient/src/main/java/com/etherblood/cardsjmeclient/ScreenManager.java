package com.etherblood.cardsjmeclient;

import com.etherblood.cardsjmeclient.appscreens.Screen;
import com.etherblood.cardsjmeclient.events.EventListener;
import com.etherblood.cardsjmeclient.events.Eventbus;
import com.jme3.scene.Node;
import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author Philipp
 */
public class ScreenManager<T> {
    private final Node guiNode;
    private final Eventbus eventbus;
    private final Map<T, Screen> screens = new HashMap<>();
    private Screen active = null;

    public ScreenManager(Node guiNode, Eventbus eventbus) {
        this.eventbus = eventbus;
        this.guiNode = guiNode;
    }
    
    public void selectScreen(T screenKey) {
        if(active != null) {
            active.onDetach();
            guiNode.detachChild(active.getNode());
            for (Map.Entry<Class, EventListener> entry : active.getEventListeners().entrySet()) {
                eventbus.unsubscribe(entry.getKey(), entry.getValue());
            }
        }
        active = screens.get(screenKey);
        if(active != null) {
            for (Map.Entry<Class, EventListener> entry : active.getEventListeners().entrySet()) {
                eventbus.subscribe(entry.getKey(), entry.getValue());
            }
            guiNode.attachChild(active.getNode());
            active.onAttach();
        }
    }
    
    public void addScreen(T screenKey, Screen screen) {
        screens.put(screenKey, screen);
    }
    
    public Screen removeScreen(T screenKey) {
        return screens.remove(screenKey);
    }
}
