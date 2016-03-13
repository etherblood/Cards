package com.etherblood.cardsmatch.cardgame.match;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Philipp
 */
public class MatchContext {

    private final List<Object> beans;

    MatchContext(List<Object> beans) {
        this.beans = new ArrayList<>(beans);
        populateAll(beans);
    }

    final void populateAll(List<Object> beans) {
        for (Object bean : beans) {
            populate(bean);
        }
    }

    private void populate(Object obj) {
        try {
            Class clazz = obj.getClass();
            while (clazz != null) {
                for (Field field : clazz.getDeclaredFields()) {
                    if (field.isAnnotationPresent(Autowire.class)) {
                        field.setAccessible(true);
                        Class fieldClass = field.getType();
                        field.set(obj, getWireCandidate(fieldClass));
                    }
                }
                clazz = clazz.getSuperclass();
            }
        } catch (IllegalArgumentException | IllegalAccessException ex) {
            throw new RuntimeException(ex);
        }
    }

    public <T> T getBean(Class<T> beanClass) {
        return (T) getWireCandidate(beanClass);
    }

    private Object getWireCandidate(Class fieldClass) {
        ArrayList<Object> result = new ArrayList<>();
        for (Object wireCandidate : beans) {
            if (fieldClass.isInstance(wireCandidate)) {
                result.add(wireCandidate);
            }
        }
        return extractResult(result, fieldClass);
    }

    private Object extractResult(ArrayList<Object> results, Class fieldClass) throws IllegalStateException {
        if (results.size() != 1) {
            if (results.isEmpty()) {
                throw new IllegalStateException("no bean found for " + fieldClass.getName());
            }
            throw new IllegalStateException("multiple beans found for " + fieldClass.getName() + ": " + results.toString());
        }
        return results.get(0);
    }
}
