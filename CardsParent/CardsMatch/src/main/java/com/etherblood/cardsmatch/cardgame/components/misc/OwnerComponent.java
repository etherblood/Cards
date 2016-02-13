/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.etherblood.cardsmatch.cardgame.components.misc;

import com.etherblood.entitysystem.data.EntityComponent;
import com.etherblood.entitysystem.data.EntityId;
import com.etherblood.entitysystem.filters.AbstractComponentFieldValueFilter;
import com.etherblood.entitysystem.filters.BinaryOperator;
import com.etherblood.entitysystem.filters.ComponentFilter;
import com.etherblood.entitysystem.filters.ReflectionComponentFieldValueFilter;
import java.lang.reflect.Field;

/**
 *
 * @author Philipp
 */
public class OwnerComponent implements EntityComponent {
    public static final Field PLAYER_FIELD;
    public static AbstractComponentFieldValueFilter<OwnerComponent> createPlayerFilter(BinaryOperator operator) {
//        return new ReflectionComponentFieldValueFilter<OwnerComponent>(PLAYER_FIELD, operator);
        return new AbstractComponentFieldValueFilter<OwnerComponent>(operator) {
            @Override
            protected Object fieldValue(OwnerComponent component) {
                return component.player;
            }
            @Override
            public Class<OwnerComponent> getComponentType() {
                return OwnerComponent.class;
            }
        };
    }
    public final EntityId player;

    static {
        try {
            PLAYER_FIELD = OwnerComponent.class.getField("player");
            PLAYER_FIELD.setAccessible(true);
        } catch (NoSuchFieldException | SecurityException ex) {
            throw new RuntimeException(ex);
        }
    }
    
    public OwnerComponent(EntityId playerId) {
        if(playerId == null) {
            throw new NullPointerException();
        }
        this.player = playerId;
    }
}
