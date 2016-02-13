/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.etherblood.entitysystem.filters;

/**
 *
 * @author Philipp
 */
public abstract class AbstractComponentFieldValueFilter<T> implements ComponentFilter<T> {
    protected Object value;
    protected final BinaryOperator operator;

    public AbstractComponentFieldValueFilter(BinaryOperator operator) {
        this.operator = operator;
    }

    @Override
    public boolean passesFilter(T component) {
        return operator.evaluate(value, component == null? null: fieldValue(component));
    }
    
    protected abstract Object fieldValue(T component);

    public Object getValue() {
        return value;
    }

    public void setValue(Object value) {
        this.value = value;
    }
}
