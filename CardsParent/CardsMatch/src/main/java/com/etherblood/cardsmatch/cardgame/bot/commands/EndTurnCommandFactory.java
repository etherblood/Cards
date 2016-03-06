package com.etherblood.cardsmatch.cardgame.bot.commands;

import com.etherblood.match.Autowire;
import com.etherblood.cardsmatch.cardgame.components.effects.effects.EndTurnEffectComponent;
import com.etherblood.cardsmatch.cardgame.components.misc.OwnerComponent;
import com.etherblood.cardsmatch.cardgame.components.player.ItsMyTurnComponent;
import com.etherblood.entitysystem.data.EntityComponentMapReadonly;
import com.etherblood.entitysystem.filters.AbstractComponentFieldValueFilter;
import com.etherblood.entitysystem.filters.EqualityOperator;
import com.etherblood.entitysystem.filters.FilterQuery;

/**
 *
 * @author Philipp
 */
public class EndTurnCommandFactory {
    @Autowire public EntityComponentMapReadonly data;
    
    private final AbstractComponentFieldValueFilter<OwnerComponent> ownerFilter = OwnerComponent.createPlayerFilter(new EqualityOperator());
    private final FilterQuery effectQuery = new FilterQuery()
            .setBaseClass(EndTurnEffectComponent.class)
            .addComponentFilter(ownerFilter);
    private final FilterQuery currentQuery = new FilterQuery()
            .setBaseClass(ItsMyTurnComponent.class);
    
    public Command generateEndTurnCommand() {
        ownerFilter.setValue(currentQuery.first(data));
        return new Command(effectQuery.first(data));
    }
}
