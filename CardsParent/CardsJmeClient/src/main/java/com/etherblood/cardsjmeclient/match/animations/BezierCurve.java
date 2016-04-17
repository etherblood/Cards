package com.etherblood.cardsjmeclient.match.animations;

import com.jme3.math.Transform;

/**
 *
 * @author Philipp
 */
public class BezierCurve {

    public Transform calcInplace(float weight, Transform... transforms) {
        for (int i = transforms.length - 1; i > 0; i--) {
            for (int j = 0; j < i; j++) {
                transforms[j].interpolateTransforms(transforms[j], transforms[j + 1], weight);
            }
        }
        return transforms[0];
    }
}
