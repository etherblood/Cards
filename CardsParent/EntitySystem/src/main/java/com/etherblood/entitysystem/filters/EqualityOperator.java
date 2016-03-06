package com.etherblood.entitysystem.filters;

/**
 *
 * @author Philipp
 */
public class EqualityOperator<T> implements BinaryOperator<T> {

    @Override
    public boolean evaluate(T a, T b) {
        return a == b || (a != null && a.equals(b));
    }

    @Override
    public String toString(String a, String b) {
        return a + "=" + b;
    }
    
}
