package com.etherblood.cardsjmeclient.match.animations.functions;


public class ChainedInterpolationFunction implements InterpolationFunction {
    
    private final InterpolationFunction[] functions;

    public ChainedInterpolationFunction(InterpolationFunction[] functions) {
        this.functions = functions;
    }
    
    @Override
    public float adjust(float value) {
        for (InterpolationFunction function : functions) {
            value = function.adjust(value);
        }
        return value;
    }

}
