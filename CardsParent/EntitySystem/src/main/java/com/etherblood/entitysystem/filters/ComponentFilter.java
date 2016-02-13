/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.etherblood.entitysystem.filters;

/**
 *
 * @author Philipp
 */
public interface ComponentFilter<T> {

    public Class<T> getComponentType();
    public boolean passesFilter(T component);
}
