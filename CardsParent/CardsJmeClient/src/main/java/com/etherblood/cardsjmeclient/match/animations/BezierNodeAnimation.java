package com.etherblood.cardsjmeclient.match.animations;

import com.etherblood.cardsjmeclient.match.animations.base.Animation;
import com.etherblood.cardsjmeclient.match.animations.functions.InterpolationFunction;
import com.jme3.math.Transform;
import com.jme3.scene.Node;

/**
 *
 * @author Philipp
 */
public class BezierNodeAnimation implements Animation {
    private static final BezierCurve BEZIER_CURVE = new BezierCurve();
    private final Node node;
    private final float duration;
    private final Transform[] path;
    private final Transform[] pathInplace;

    public BezierNodeAnimation(Node node, float duration, Transform[] path) {
        this.node = node;
        this.duration = duration;
        this.path = path;
        this.pathInplace = new Transform[path.length];
        for (int i = 0; i < path.length; i++) {
            pathInplace[i] = new Transform();
        }
    }

    @Override
    public float durationSeconds() {
        return duration;
    }

    @Override
    public void update(float totalElapsedSeconds) {
        for (int i = 0; i < path.length; i++) {
            pathInplace[i].set(path[i]);
        }
        node.setLocalTransform(BEZIER_CURVE.calcInplace(totalElapsedSeconds / duration, pathInplace));
    }

    @Override
    public void init() {
        if(!path[0].equals(node.getLocalTransform())) {
            throw new IllegalStateException();
        }
    }

    @Override
    public void cleanup() {
        node.setLocalTransform(path[path.length - 1]);
    }

}
