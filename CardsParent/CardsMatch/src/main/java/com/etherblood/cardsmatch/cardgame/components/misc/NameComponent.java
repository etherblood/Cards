/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.etherblood.cardsmatch.cardgame.components.misc;

import com.etherblood.cardsmatch.cardgame.components.ComponentAlias;
import com.etherblood.entitysystem.data.EntityComponent;
import com.etherblood.entitysystem.filters.AbstractComponentFieldValueFilter;
import com.etherblood.entitysystem.filters.BinaryOperator;
import com.etherblood.entitysystem.filters.ReflectionComponentFieldValueFilter;
import java.lang.reflect.Field;

/**
 *
 * @author Philipp
 */
@ComponentAlias(name="name")
public class NameComponent implements EntityComponent {
    public static final Field NAME_FIELD;
    public static AbstractComponentFieldValueFilter<NameComponent> createNameFilter(BinaryOperator operator) {
//        return new ReflectionComponentFieldValueFilter<NameComponent>(NAME_FIELD, operator);
        return new AbstractComponentFieldValueFilter<NameComponent>(operator) {
            @Override
            protected Object fieldValue(NameComponent component) {
                return component.name;
            }
            @Override
            public Class<NameComponent> getComponentType() {
                return NameComponent.class;
            }
        };
    }
    public final String name;

    static {
        try {
            NAME_FIELD = NameComponent.class.getField("name");
            NAME_FIELD.setAccessible(true);
        } catch (NoSuchFieldException | SecurityException ex) {
            throw new RuntimeException(ex);
        }
    }
    
    public NameComponent(String name) {
        if(name == null) {
            throw new NullPointerException();
        }
        this.name = name;
    }
}
