package com.jme3.math;

import com.jme3.network.serializing.Serializable;

/**
 *
 * @author Philipp
 */
@Serializable
public class Vector3f {
    //this class is needed because jme3 network registers Vector3f by default, but I'd like to avoid including jme3-core.jar
}
