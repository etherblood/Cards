package com.etherblood.firstruleset.logic.player;

import com.etherblood.cardsmatch.cardgame.components.ComponentAlias;
import com.etherblood.entitysystem.data.EntityComponent;
import com.etherblood.entitysystem.data.EntityId;
import com.etherblood.entitysystem.filters.AbstractComponentFieldValueFilter;
import com.etherblood.entitysystem.filters.BinaryOperator;

/**
 *
 * @author Philipp
 */
@ComponentAlias(name="owner")
public class OwnerComponent implements EntityComponent {
    public static AbstractComponentFieldValueFilter<OwnerComponent> createPlayerFilter(BinaryOperator operator) {
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
    
    public OwnerComponent(EntityId playerId) {
        if(playerId == null) {
            throw new NullPointerException();
        }
        this.player = playerId;
    }
}
