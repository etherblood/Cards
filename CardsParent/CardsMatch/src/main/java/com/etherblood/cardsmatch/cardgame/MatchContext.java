package com.etherblood.cardsmatch.cardgame;

import java.lang.reflect.Field;
import java.util.ArrayList;

/**
 *
 * @author Philipp
 */
public class MatchContext {

    private final ArrayList<Object> beans = new ArrayList<>();

    public void addBean(Object bean) {
        beans.add(bean);
    }
    
    public void repopulateAll() {
        for (Object bean : beans) {
            populate(bean);
        }
    }
    
    public void populate(Object obj) {
        try {
            Class clazz = obj.getClass();
            while(clazz != null) {
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
            if(fieldClass.isInstance(wireCandidate)) {
                result.add(wireCandidate);
            }
        }
        return extractResult(result, fieldClass);
    }

    private Object extractResult(ArrayList<Object> results, Class fieldClass) throws IllegalStateException {
        if(results.size() != 1) {
            if(results.isEmpty()) {
                throw new IllegalStateException("no bean found for " + fieldClass.getName());
            }
            String beanClassChain = "";
            for (Object object : results) {
                beanClassChain += "[" + object.getClass().getName() + "]";
            }
            throw new IllegalStateException("multiple beans found for " + fieldClass.getName() + ": " + beanClassChain);
        }
        return results.get(0);
    }
}
