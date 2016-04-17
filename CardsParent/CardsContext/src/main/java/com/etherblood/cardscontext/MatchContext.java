package com.etherblood.cardscontext;

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
