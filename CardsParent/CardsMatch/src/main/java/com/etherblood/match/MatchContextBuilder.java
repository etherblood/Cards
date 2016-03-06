package com.etherblood.match;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import javax.annotation.PostConstruct;

/**
 *
 * @author Philipp
 */
public class MatchContextBuilder {
    private final ArrayList<Object> beans = new ArrayList<>();
    private final ArrayList<Object> passiveBeans = new ArrayList<>();
    
    public void addBean(Object bean) {
        beans.add(bean);
    }
    public void addPassiveBean(Object bean) {
        passiveBeans.add(bean);
    }
    
    public MatchContext build() {
        MatchContext context = new MatchContext(beans);
        context.populateAll(passiveBeans);
        for (Object object : beans) {
            postConstruct(object);
        }
        for (Object object : passiveBeans) {
            postConstruct(object);
        }
        return context;
    }
    
    private void postConstruct(Object bean) {
        for (Method method : bean.getClass().getMethods()) {
            if(method.isAnnotationPresent(PostConstruct.class)) {
                try {
                    method.invoke(bean);
                } catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException ex) {
                    throw new RuntimeException(ex);
                }
            }
        }
    }
}
