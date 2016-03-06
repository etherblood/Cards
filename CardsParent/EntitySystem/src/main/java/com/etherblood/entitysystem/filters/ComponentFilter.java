package com.etherblood.entitysystem.filters;

/**
 *
 * @author Philipp
 */
public interface ComponentFilter<T> {

    public Class<T> getComponentType();
    public boolean passesFilter(T component);
}
