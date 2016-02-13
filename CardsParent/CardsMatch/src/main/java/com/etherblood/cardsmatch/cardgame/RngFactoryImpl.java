package com.etherblood.cardsmatch.cardgame;

import java.util.Random;


public class RngFactoryImpl implements RngFactory {
    private final Random rng = new Random();
    
    @Override
    public int nextInt(int max) {
        return rng.nextInt(max);
    }

}
