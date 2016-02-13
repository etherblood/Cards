/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.etherblood.entitysystem.filters;

/**
 *
 * @author Philipp
 */
public class IsNullOperator<T> implements UnaryOperator<T>{

    @Override
    public boolean evaluate(T a) {
        return a == null;
    }

    @Override
    public String toString(String a) {
        return '~' + a;
    }
    
}
