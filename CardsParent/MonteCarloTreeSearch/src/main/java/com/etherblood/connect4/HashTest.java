package com.etherblood.connect4;

import java.util.HashSet;

/**
 *
 * @author Philipp
 */
public class HashTest<T> {
    private final HashSet<Integer> uniqueMasked = new HashSet<>();
    private final HashSet<T> uniqueHashes = new HashSet<>();
    private final int mod;

    public HashTest(int mod) {
        this.mod = mod;
    }
    
    public void reset() {
        uniqueHashes.clear();
        uniqueMasked.clear();
    }
    
    public void add(T obj) {
        if(uniqueHashes.add(obj)) {
            uniqueMasked.add(obj.hashCode() % mod);
        }
    }
    
    public float quality() {
        return (float)uniqueMasked.size() / uniqueHashes.size();
    }
}
