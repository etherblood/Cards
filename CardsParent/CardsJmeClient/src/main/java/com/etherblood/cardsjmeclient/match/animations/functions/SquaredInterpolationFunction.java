package com.etherblood.cardsjmeclient.match.animations.functions;


public class SquaredInterpolationFunction implements InterpolationFunction {

    @Override
    public float adjust(float value) {
        return value * value;
    }

}
