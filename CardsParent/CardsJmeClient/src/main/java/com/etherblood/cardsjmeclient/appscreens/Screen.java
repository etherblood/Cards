package com.etherblood.cardsjmeclient.appscreens;

import com.etherblood.cardsjmeclient.events.EventListener;
import com.etherblood.cardsjmeclient.events.Eventbus;
import com.jme3.scene.Spatial;
import java.util.Map;

/**
 *
 * @author Philipp
 */
public interface Screen {

    void bind(Eventbus eventbus);
    Spatial getNode();
    Map<Class, EventListener> getEventListeners();
    void onAttach();
    void onDetach();
//    void bind(Eventbus eventbus, Node parent);
}
