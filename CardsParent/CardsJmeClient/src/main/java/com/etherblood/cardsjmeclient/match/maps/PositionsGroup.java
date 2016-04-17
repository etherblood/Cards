package com.etherblood.cardsjmeclient.match.maps;

import com.jme3.math.Transform;

/**
 *
 * @author Philipp
 */
public abstract class PositionsGroup {
    
    public abstract Transform position(int index, int numTotal);
}
