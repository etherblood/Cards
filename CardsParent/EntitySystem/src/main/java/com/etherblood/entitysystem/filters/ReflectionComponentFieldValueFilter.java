/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.etherblood.entitysystem.filters;

import java.lang.reflect.Field;

/**
 *
 * @author Philipp
 */
public class ReflectionComponentFieldValueFilter<T> extends AbstractComponentFieldValueFilter<T> {
    private final Field field;
    
    public ReflectionComponentFieldValueFilter(Field field) {
        this(field, new EqualityOperator<>());
    }
    public ReflectionComponentFieldValueFilter(Field field, BinaryOperator operator) {
        this(field, operator, null);
    }
    public ReflectionComponentFieldValueFilter(Field field, BinaryOperator operator, Object value) {
        super(operator);
        this.field = field;
        this.value = value;
    }

    @Override
    public Class getComponentType() {
        return field.getDeclaringClass();
    }
    
    @Override
    protected Object fieldValue(T component) {
        try {
            return field.get(component);
        } catch (IllegalArgumentException | IllegalAccessException exeption) {
            throw new RuntimeException( "Error retrieving field[" + field + "] of " + component, exeption);
        }
    }

    @Override
    public String toString() {
        return operator.toString(field.getDeclaringClass().getSimpleName().replace("Component", "") + "." + field.getName(), value.toString());
    }
}
