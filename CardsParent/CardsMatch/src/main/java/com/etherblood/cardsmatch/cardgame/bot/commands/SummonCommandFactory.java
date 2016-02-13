package com.etherblood.cardsmatch.cardgame.bot.commands;

import com.etherblood.cardsmatch.cardgame.Autowire;
import com.etherblood.cardsmatch.cardgame.components.battle.stats.ManaCostComponent;
import com.etherblood.cardsmatch.cardgame.components.cards.cardZone.HandCardComponent;
import com.etherblood.cardsmatch.cardgame.components.effects.EffectTriggerEntityComponent;
import com.etherblood.cardsmatch.cardgame.components.effects.effects.SummonEffectComponent;
import com.etherblood.cardsmatch.cardgame.components.misc.OwnerComponent;
import com.etherblood.cardsmatch.cardgame.components.player.ItsMyTurnComponent;
import com.etherblood.cardsmatch.cardgame.components.player.ManaComponent;
import com.etherblood.entitysystem.data.EntityComponentMapReadonly;
import com.etherblood.entitysystem.data.EntityId;
import com.etherblood.entitysystem.filters.AbstractComponentFieldValueFilter;
import com.etherblood.entitysystem.filters.EqualityOperator;
import com.etherblood.entitysystem.filters.FilterQuery;
import java.util.List;

/**
 *
 * @author Philipp
 */
public class SummonCommandFactory {
    @Autowire private EntityComponentMapReadonly data;
    
    private final AbstractComponentFieldValueFilter<OwnerComponent> ownerFilter = OwnerComponent.createPlayerFilter(new EqualityOperator());
    private final AbstractComponentFieldValueFilter<EffectTriggerEntityComponent> triggerFilter = EffectTriggerEntityComponent.createTriggerFilter(new EqualityOperator());
    private final FilterQuery handMinions = new FilterQuery()
            .setBaseClass(HandCardComponent.class)
            .addComponentFilter(ownerFilter);
    private final FilterQuery currentQuery = new FilterQuery()
            .setBaseClass(ItsMyTurnComponent.class);
    private final FilterQuery summonQuery = new FilterQuery()
            .setBaseClass(SummonEffectComponent.class)
            .addComponentFilter(triggerFilter);
    
    public void generateSummonCommands(List<Command> commands) {
        EntityId currentPlayer = currentQuery.first(data);
        ownerFilter.setValue(currentPlayer);
        ManaComponent manaComp = data.get(currentPlayer, ManaComponent.class);
        int mana = manaComp == null? 0: manaComp.mana;
        for (EntityId minion : handMinions.list(data)) {
            ManaCostComponent costComp = data.get(minion, ManaCostComponent.class);
            int cost = costComp == null? 0: costComp.mana;
            if(mana >= cost) {
                triggerFilter.setValue(minion);
                EntityId effect = summonQuery.first(data);
                commands.add(new Command(effect));
            }
        }
    }

}
