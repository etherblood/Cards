/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.etherblood.entitysystem.filters;

/**
 *
 * @author Philipp
 */
public class ComponentClassFilter<T> implements ComponentFilter<T> {
    private final Class<T> type;
    private final UnaryOperator<T> operator;

    public ComponentClassFilter(Class<T> type) {
        this(type, new IsNotNullOperator<T>());
    }

    public ComponentClassFilter(Class<T> type, UnaryOperator<T> operator) {
        this.type = type;
        this.operator = operator;
    }

    @Override
    public Class<T> getComponentType() {
        return type;
    }

    @Override
    public boolean passesFilter(T component) {
        return operator.evaluate(component);
    }

    @Override
    public String toString() {
        return operator.toString(type.getSimpleName().replace("Component", ""));
    }
}
