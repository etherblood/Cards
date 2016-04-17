package com.etherblood.cardsjmeclient.match.animations.functions;

public class SmootherstepInterpolationFunction implements InterpolationFunction {

    @Override
    public float adjust(float value) {
        return value * value * value * (value * (value * 6 - 15) + 10);
    }

}
