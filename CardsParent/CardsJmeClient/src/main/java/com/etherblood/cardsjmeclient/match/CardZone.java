package com.etherblood.cardsjmeclient.match;

import com.jme3.math.Transform;

/**
 *
 * @author Philipp
 */
public interface CardZone {
    Transform createAnchor();
    void removeAnchor(Transform anchor);
}
