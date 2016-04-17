package com.etherblood.cardscontext;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;

/**
 *
 * @author Philipp
 */
public class MatchContextBuilder {
    private ArrayList<Object> beans = new ArrayList<>();
    
//    public <T> T removeBean(Class<T> beanClass) {
//        for (Object bean : beans) {
//            if(beanClass.isInstance(bean)) {
//                beans.remove(bean);
//                return (T) bean;
//            }
//        }
//        return null;
//    }
    public void addBean(Object bean) {
        beans.add(bean);
    }
    
    public MatchContext build() {
        MatchContext context = new MatchContext(beans);
        populateAll(context, beans);
        for (Object object : beans) {
            postConstruct(object);
        }
        beans = null;
        return context;
    }

    private void populateAll(MatchContext context, List<Object> beans) {
        for (Object bean : beans) {
            populate(context, bean);
        }
    }

    private void populate(MatchContext context, Object obj) {
        try {
            Class clazz = obj.getClass();
            while (clazz != null) {
                for (Field field : clazz.getDeclaredFields()) {
                    if (field.isAnnotationPresent(Autowire.class)) {
                        field.setAccessible(true);
                        Class fieldClass = field.getType();
                        field.set(obj, context.getBean(fieldClass));
                    }
                }
                clazz = clazz.getSuperclass();
            }
        } catch (IllegalArgumentException | IllegalAccessException ex) {
            throw new RuntimeException(ex);
        }
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
