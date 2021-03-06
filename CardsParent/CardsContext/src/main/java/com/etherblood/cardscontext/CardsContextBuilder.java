package com.etherblood.cardscontext;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;

/**
 *
 * @author Philipp
 */
public class CardsContextBuilder {
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
    
    public CardsContext build() {
        CardsContext context = new CardsContext(beans);
        populateAll(context, beans);
        for (Object object : beans) {
            postConstruct(object);
        }
        beans = null;
        return context;
    }

    private void populateAll(CardsContext context, List<Object> beans) {
        for (Object bean : beans) {
            populate(context, bean);
        }
    }

    private void populate(CardsContext context, Object obj) {
        try {
            Class clazz = obj.getClass();
            while (clazz != null) {
                for (Field field : clazz.getDeclaredFields()) {
                    if (field.isAnnotationPresent(Autowire.class)) {
                        field.setAccessible(true);
                        Class fieldClass = field.getType();
                        if(List.class.isAssignableFrom(fieldClass)) {
                            ParameterizedType fieldType = (ParameterizedType)field.getGenericType();
                            field.set(obj, context.getBeans((Class) fieldType.getActualTypeArguments()[0]));
                        } else {
                            field.set(obj, context.getBean(fieldClass));
                        }
                    }
                }
                for (Method method : clazz.getDeclaredMethods()) {
                    if (method.isAnnotationPresent(Autowire.class)) {
                        method.setAccessible(true);
                        Class[] methodArgClasses = method.getParameterTypes();
                        Object[] args = new Object[methodArgClasses.length];
                        for (int i = 0; i < methodArgClasses.length; i++) {
                            Class argClass = methodArgClasses[i];
                            if(List.class.isAssignableFrom(argClass)) {
                                ParameterizedType argType = (ParameterizedType)method.getGenericParameterTypes()[i];
                                args[i] = context.getBeans((Class) argType.getActualTypeArguments()[0]);
                            } else {
                                args[i] = context.getBean(argClass);
                            }
                        }
                        method.invoke(obj, args);
                    }
                }
                clazz = clazz.getSuperclass();
            }
        } catch (Exception ex) {
            System.out.println("error when populating bean: " + obj);
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
