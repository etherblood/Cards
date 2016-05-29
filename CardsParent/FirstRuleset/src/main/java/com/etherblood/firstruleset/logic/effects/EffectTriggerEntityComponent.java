/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.etherblood.firstruleset.logic.effects;

import com.etherblood.entitysystem.data.EntityComponent;
import com.etherblood.entitysystem.data.EntityId;
import com.etherblood.entitysystem.filters.AbstractComponentFieldValueFilter;
import com.etherblood.entitysystem.filters.BinaryOperator;
import com.etherblood.entitysystem.filters.EqualityOperator;
import java.lang.reflect.Field;

/**
 *
 * @author Philipp
 */
public class EffectTriggerEntityComponent implements EntityComponent {
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
    
    public static AbstractComponentFieldValueFilter<EffectTriggerEntityComponent> equalsFilter(EntityId value) {
        AbstractComponentFieldValueFilter<EffectTriggerEntityComponent> filter = createTriggerFilter(EqualityOperator.INSTANCE);
        filter.setValue(value);
        return filter;
    }

    public final EntityId entity;

    public EffectTriggerEntityComponent(EntityId entity) {
        this.entity = entity;
    }
}
