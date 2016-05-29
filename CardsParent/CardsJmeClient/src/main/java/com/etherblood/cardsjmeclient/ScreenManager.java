package com.etherblood.cardsjmeclient;

import com.etherblood.cardscontext.Autowire;
import com.etherblood.cardsjmeclient.appscreens.Screen;
import com.etherblood.cardsjmeclient.events.EventListener;
import com.etherblood.cardsjmeclient.events.Eventbus;
import com.etherblood.cardsjmeclient.events.ScreenRequestEvent;
import com.etherblood.cardsjmeclient.states.GuiState;
import java.util.EnumMap;
import java.util.List;
import java.util.Map;
import javax.annotation.PostConstruct;

/**
 *
 * @author Philipp
 */
public class ScreenManager {
    @Autowire
    private GuiState guiState;
    @Autowire
    private Eventbus eventbus;
    private final Map<ScreenKeys, Screen> screens = new EnumMap<>(ScreenKeys.class);
    private Screen active = null;
    
    @Autowire
    private void setScreens(List<Screen> screenList) {
        screens.clear();
        for (Screen screen : screenList) {
            screens.put(screen.getScreenKey(), screen);
        }
    }
    
//    @PostConstruct
//    private void init() {
//        eventbus.subscribe(ScreenRequestEvent.class, new EventListener<ScreenRequestEvent>() {
//            @Override
//            public void onEvent(ScreenRequestEvent event) {
//                selectScreen(event.key);
//            }
//        });
//    }
    
    public void selectScreen(ScreenKeys screenKey) {
        if(active != null) {
            active.onDetach();
            guiState.getGuiNode().detachChild(active.getNode());
            for (Map.Entry<Class, EventListener> entry : active.getEventListeners().entrySet()) {
                eventbus.unsubscribe(entry.getKey(), entry.getValue());
            }
        }
        active = screens.get(screenKey);
        if(active != null) {
            for (Map.Entry<Class, EventListener> entry : active.getEventListeners().entrySet()) {
                eventbus.subscribe(entry.getKey(), entry.getValue());
            }
            guiState.getGuiNode().attachChild(active.getNode());
            active.onAttach();
        }
    }
    
    public void addScreen(ScreenKeys screenKey, Screen screen) {
        screens.put(screenKey, screen);
    }
    
    public Screen removeScreen(ScreenKeys screenKey) {
        return screens.remove(screenKey);
    }
}
