package com.etherblood.cardsjmeclient.match.animations;

import com.etherblood.cardsjmeclient.match.animations.base.Animation;
import com.etherblood.cardsjmeclient.match.animations.functions.InterpolationFunction;
import com.jme3.math.Transform;
import com.jme3.scene.Node;

/**
 *
 * @author Philipp
 */
public class TransformNodeAnimation implements Animation {
    private final Node node;
    private final float duration;
    private final InterpolationFunction func;
    private final Transform destination;
    private Transform source;

    public TransformNodeAnimation(Node node, float duration, InterpolationFunction func, Transform destination) {
        this.node = node;
        this.duration = duration;
        this.func = func;
        this.destination = destination;
    }

    @Override
    public float durationSeconds() {
        return duration;
    }

    @Override
    public void update(float totalElapsedSeconds) {
        node.getLocalTransform().interpolateTransforms(source, destination, func.adjust(totalElapsedSeconds / duration));
    }

    @Override
    public void init() {
        source = node.getLocalTransform();
    }

    @Override
    public void cleanup() {
        node.setLocalTransform(destination);
    }

}
