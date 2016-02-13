package com.etherblood.eventsystem;

import java.util.ArrayDeque;
import java.util.ArrayList;

/**
 *
 * @author Philipp
 */
public class GameEventDataStack {
    private final ArrayList<Object> values = new ArrayList<>();
    private final ArrayList<Class> keys = new ArrayList<>();
    private final ArrayDeque<Integer> layerCounts = new ArrayDeque<>();
    
    protected int depth() {
        return layerCounts.size();
    }
    
    protected void up() {
        layerCounts.push(values.size());
    }
    
    protected void down() {
        int iterations = values.size() - layerCounts.pop();
        for (int i = 0; i < iterations; i++) {
            int lastIndex = values.size() - 1;
            values.remove(lastIndex);
            keys.remove(lastIndex);
        }
    }
    
    public <T> void push(T value) {
        keys.add(value.getClass());
        values.add(value);
    }
    
    public <T> T get(Class<T> key) {
        for (int i = values.size() - 1; i >= 0; i--) {
            if(key == keys.get(i)) {
                return (T)values.get(i);
            }
        }
        return null;
    }
    
    public void clear() {
        layerCounts.clear();
        keys.clear();
        values.clear();
    }
    
//    public void pop() {
//        int lastIndex = values.size() - 1;
//        values.remove(lastIndex);
//        keys.remove(lastIndex);
//    }
}
