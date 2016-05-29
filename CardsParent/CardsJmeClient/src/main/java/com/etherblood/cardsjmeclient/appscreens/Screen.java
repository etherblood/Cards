package com.etherblood.cardsjmeclient.appscreens;

import com.etherblood.cardsjmeclient.ScreenKeys;
import com.etherblood.cardsjmeclient.events.EventListener;
import com.jme3.scene.Spatial;
import java.util.Map;

/**
 *
 * @author Philipp
 */
public interface Screen {

    Spatial getNode();
    Map<Class, EventListener> getEventListeners();
    void onAttach();
    void onDetach();
    ScreenKeys getScreenKey();
}
