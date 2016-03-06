/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.etherblood.cardsmatch.cardgame.components.effects;

import com.etherblood.entitysystem.data.EntityComponent;
import com.etherblood.entitysystem.data.EntityId;
import com.etherblood.entitysystem.filters.AbstractComponentFieldValueFilter;
import com.etherblood.entitysystem.filters.BinaryOperator;
import java.lang.reflect.Field;

/**
 *
 * @author Philipp
 */
public class EffectTriggerEntityComponent implements EntityComponent {
    public final static Field ENTITY_FIELD;
    public static AbstractComponentFieldValueFilter<EffectTriggerEntityComponent> createTriggerFilter(BinaryOperator operator) {
        return new AbstractComponentFieldValueFilter<EffectTriggerEntityComponent>(operator) {
            @Override
            protected Object fieldValue(EffectTriggerEntityComponent component) {
                return component.entity;
            }
            @Override
            public Class<EffectTriggerEntityComponent> getComponentType() {
                return EffectTriggerEntityComponent.class;
            }
        };
    }
    static{
        try {
            ENTITY_FIELD = EffectTriggerEntityComponent.class.getDeclaredField("entity");
            ENTITY_FIELD.setAccessible(true);
        } catch (NoSuchFieldException | SecurityException ex) {
            throw new RuntimeException(ex);
        }
    }

    public final EntityId entity;

    public EffectTriggerEntityComponent(EntityId entity) {
        this.entity = entity;
    }
}
