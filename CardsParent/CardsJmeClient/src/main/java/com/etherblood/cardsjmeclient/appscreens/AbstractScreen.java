package com.etherblood.cardsjmeclient.appscreens;

import com.etherblood.cardscontext.Autowire;
import com.etherblood.cardsjmeclient.events.EventListener;
import com.etherblood.cardsjmeclient.events.Eventbus;
import com.jme3.scene.Spatial;
import com.simsilica.lemur.Container;
import java.util.HashMap;
import java.util.Map;

public abstract class AbstractScreen implements Screen {

    private final Container container = new Container();
    private final HashMap<Class, EventListener> listeners = new HashMap<>();
    @Autowire private Eventbus eventbus;

    @Override
    public Spatial getNode() {
        return container;
    }

    @Override
    public Map<Class, EventListener> getEventListeners() {
        return listeners;
    }

    public Container getContainer() {
        return container;
    }

    public HashMap<Class, EventListener> getListeners() {
        return listeners;
    }

    @Override
    public void onAttach() {
    }

    @Override
    public void onDetach() {
    }

    public Eventbus getEventbus() {
        return eventbus;
    }

}
