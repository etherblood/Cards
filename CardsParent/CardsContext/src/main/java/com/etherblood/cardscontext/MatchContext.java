package com.etherblood.cardscontext;

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
        return extractResult(getBeans(beanClass), beanClass);
    }


    public <T> List<T> getBeans(Class<T> fieldClass) {
        ArrayList<T> result = new ArrayList<>();
        for (Object wireCandidate : beans) {
            if (fieldClass.isInstance(wireCandidate)) {
                result.add((T) wireCandidate);
            }
        }
        return result;
    }

    private <T> T extractResult(List<T> results, Class<T> fieldClass) throws IllegalStateException {
        if (results.size() != 1) {
            if (results.isEmpty()) {
                throw new IllegalStateException("no bean found for " + fieldClass.getName());
            }
            throw new IllegalStateException("multiple beans found for " + fieldClass.getName() + ": " + results.toString());
        }
        return results.get(0);
    }
}
