package com.etherblood.cardsjmeclient.match.animations.functions;


public class MultipliedInterpolationFunction implements InterpolationFunction {
    
    private final InterpolationFunction[] functions;

    public MultipliedInterpolationFunction(InterpolationFunction[] functions) {
        this.functions = functions;
    }
    
    @Override
    public float adjust(float value) {
        float result = 1;
        for (InterpolationFunction function : functions) {
            result *= function.adjust(value);
        }
        return result;
    }

}
