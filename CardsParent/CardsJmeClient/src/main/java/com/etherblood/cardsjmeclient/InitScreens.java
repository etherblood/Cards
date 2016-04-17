package com.etherblood.cardsjmeclient;

import com.etherblood.cardsjmeclient.appscreens.ArrangeMatchScreen;
import com.etherblood.cardsjmeclient.appscreens.ErrorScreen;
import com.etherblood.cardsjmeclient.appscreens.LoginScreen;
import com.etherblood.cardsjmeclient.appscreens.MatchScreen;
import com.etherblood.cardsjmeclient.appscreens.Screen;
import com.etherblood.cardsjmeclient.events.AppStartedEvent;
import com.etherblood.cardsjmeclient.events.EventListener;
import com.etherblood.cardsjmeclient.events.Eventbus;
import com.etherblood.cardsjmeclient.events.ExceptionEvent;
import com.etherblood.cardsnetworkshared.master.updates.LoginSuccess;
import com.etherblood.cardsnetworkshared.match.updates.GameOver;
import com.etherblood.cardsnetworkshared.match.updates.JoinedMatchUpdate;
import com.jme3.scene.Node;
import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author Philipp
 */
public class InitScreens {
    public ScreenManager<ScreenKeys> create(Node guiNode, Eventbus eventbus) {
        final ScreenManager<ScreenKeys> manager = new ScreenManager<>(guiNode, eventbus);
        eventbus.subscribe(JoinedMatchUpdate.class, new EventListener<JoinedMatchUpdate>() {
            @Override
            public void onEvent(JoinedMatchUpdate event) {
                manager.selectScreen(ScreenKeys.MATCH);
            }
        });
        eventbus.subscribe(GameOver.class, new EventListener<GameOver>() {
            @Override
            public void onEvent(GameOver update) {
                manager.selectScreen(ScreenKeys.LOBBY);
            }
        });
        eventbus.subscribe(LoginSuccess.class, new EventListener<LoginSuccess>() {
            @Override
            public void onEvent(LoginSuccess event) {
                manager.selectScreen(ScreenKeys.LOBBY);
            }
        });
        eventbus.subscribe(ExceptionEvent.class, new EventListener<ExceptionEvent>() {
            @Override
            public void onEvent(ExceptionEvent event) {
                manager.selectScreen(ScreenKeys.ERROR);
            }
        });
        eventbus.subscribe(AppStartedEvent.class, new EventListener<AppStartedEvent>() {
            @Override
            public void onEvent(AppStartedEvent event) {
                manager.selectScreen(ScreenKeys.LOGIN);
            }
        });
        Map<ScreenKeys, Screen> screens = createScreens();
        for (Map.Entry<ScreenKeys, Screen> entry : screens.entrySet()) {
            manager.addScreen(entry.getKey(), entry.getValue());
            entry.getValue().bind(eventbus);
        }
        return manager;
    }
    
    private Map<ScreenKeys, Screen> createScreens() {
        HashMap<ScreenKeys, Screen> map = new HashMap<>();
        map.put(ScreenKeys.LOGIN, new LoginScreen());
        map.put(ScreenKeys.MATCH, new MatchScreen());
        map.put(ScreenKeys.LOBBY, new ArrangeMatchScreen());
        map.put(ScreenKeys.ERROR, new ErrorScreen());
        return map;
    }
}