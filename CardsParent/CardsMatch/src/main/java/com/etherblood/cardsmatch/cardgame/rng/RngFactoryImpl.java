package com.etherblood.cardsmatch.cardgame.rng;

import java.util.ArrayList;
import java.util.Random;


public class RngFactoryImpl implements RngFactory {
    private final Random rng = new Random();
    private final ArrayList<RngListener> listeners = new ArrayList<>();
    
    @Override
    public int nextInt(int max) {
        int result = rng.nextInt(max);
        for (RngListener rngListener : listeners) {
            rngListener.onRng(result, max);
        }
        return result;
    }

    public void addListener(RngListener listener) {
        listeners.add(listener);
    }
}
