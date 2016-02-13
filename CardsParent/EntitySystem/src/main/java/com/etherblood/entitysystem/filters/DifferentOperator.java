/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.etherblood.entitysystem.filters;

/**
 *
 * @author Philipp
 */
public class DifferentOperator<T> implements BinaryOperator<T> {

    @Override
    public boolean evaluate(T a, T b) {
        return !(a == b || (a != null && a.equals(b)));
    }

    @Override
    public String toString(String a, String b) {
        return '~' + a + '=' + b;
    }
}
