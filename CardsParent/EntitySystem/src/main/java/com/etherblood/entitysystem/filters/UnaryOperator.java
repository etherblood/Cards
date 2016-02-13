/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.etherblood.entitysystem.filters;

/**
 *
 * @author Philipp
 */
public interface UnaryOperator<T> {
    boolean evaluate(T a);
    String toString(String a);
}
