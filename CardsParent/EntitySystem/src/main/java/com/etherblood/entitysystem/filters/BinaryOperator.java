/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.etherblood.entitysystem.filters;

/**
 *
 * @author Philipp
 */
public interface BinaryOperator<T> {
    boolean evaluate(T a, T b);
    String toString(String a, String b);
}
