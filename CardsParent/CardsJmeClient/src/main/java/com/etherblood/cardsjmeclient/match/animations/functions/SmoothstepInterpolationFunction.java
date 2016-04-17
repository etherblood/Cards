package com.etherblood.cardsjmeclient.match.animations.functions;

public class SmoothstepInterpolationFunction implements InterpolationFunction {

    @Override
    public float adjust(float value) {
        return value * value * (3 - 2 * value);
    }

}
