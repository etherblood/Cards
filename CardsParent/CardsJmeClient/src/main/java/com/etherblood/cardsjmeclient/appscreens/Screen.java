package com.etherblood.cardsjmeclient.appscreens;

import com.etherblood.cardsjmeclient.events.Eventbus;
import com.jme3.scene.Node;

/**
 *
 * @author Philipp
 */
public interface Screen {

    void bind(Eventbus eventbus, Node parent);
}
