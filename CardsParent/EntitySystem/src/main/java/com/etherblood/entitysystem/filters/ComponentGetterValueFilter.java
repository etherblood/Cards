/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.etherblood.entitysystem.filters;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 *
 * @author Philipp
 */
public class ComponentGetterValueFilter<T> implements ComponentFilter<T> {
    private final Method method;
    private Object value;
    private final BinaryOperator operator;
    
    public ComponentGetterValueFilter(Method method) {
        this(method, new EqualityOperator<>());
    }
    public ComponentGetterValueFilter(Method method, BinaryOperator operator) {
        this(method, operator, null);
    }
    public ComponentGetterValueFilter(Method method, BinaryOperator operator, Object value) {
        this.method = method;
        this.value = value;
        this.operator = operator;
    }

    @Override
    public Class getComponentType() {
        return method.getDeclaringClass();
    }

    @Override
    public boolean passesFilter(T component) {
        if(component == null) {
            return false;
        }
        Object getterValue = getComponentGetterValue(component);
        return operator.evaluate(value, getterValue);
    }

    public Object getValue() {
        return value;
    }

    public void setValue(Object value) {
        this.value = value;
    }

    private Object getComponentGetterValue(T component) throws RuntimeException {
        try {
            return method.invoke(component);
        } catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException ex) {
            throw new RuntimeException( "Error retrieving " + method + "() of:" + component, ex);
        }
    }
}
