package com.etherblood.cardsjmeclient.match.animations.functions;


public class SquareRootedInterpolationFunction implements InterpolationFunction {

    @Override
    public float adjust(float value) {
        return (float) Math.sqrt(value);
    }

}
