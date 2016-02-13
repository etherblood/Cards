package com.etherblood.cardsmatch.cardgame.bot.commands;

import com.etherblood.cardsmatch.cardgame.Autowire;
import com.etherblood.cardsmatch.cardgame.components.battle.buffs.AttackCountComponent;
import com.etherblood.cardsmatch.cardgame.components.battle.buffs.ChargeComponent;
import com.etherblood.cardsmatch.cardgame.components.battle.buffs.SummonSicknessComponent;
import com.etherblood.cardsmatch.cardgame.components.battle.buffs.TauntComponent;
import com.etherblood.cardsmatch.cardgame.components.battle.stats.AttackComponent;
import com.etherblood.cardsmatch.cardgame.components.battle.stats.HealthComponent;
import com.etherblood.cardsmatch.cardgame.components.cards.cardZone.BoardCardComponent;
import com.etherblood.cardsmatch.cardgame.components.effects.EffectTriggerEntityComponent;
import com.etherblood.cardsmatch.cardgame.components.effects.effects.AttackEffectComponent;
import com.etherblood.cardsmatch.cardgame.components.misc.OwnerComponent;
import com.etherblood.cardsmatch.cardgame.components.player.ItsMyTurnComponent;
import com.etherblood.entitysystem.data.EntityComponentMapReadonly;
import com.etherblood.entitysystem.data.EntityId;
import com.etherblood.entitysystem.filters.AbstractComponentFieldValueFilter;
import com.etherblood.entitysystem.filters.EqualityOperator;
import com.etherblood.entitysystem.filters.FilterQuery;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 *
 * @author Philipp
 */
public class AttackCommandFactory {
    @Autowire private EntityComponentMapReadonly data;
    
    private final FilterQuery currentQuery = new FilterQuery()
            .setBaseClass(ItsMyTurnComponent.class);
    
    
    public void generateAttackCommands(List<Command> commands) {
        EntityId currentPlayer = currentQuery.first(data);
        
        Set<EntityId> boardCards = data.entities(BoardCardComponent.class);
        Set<EntityId> attackers = new HashSet<>();
        Set<EntityId> defenders = new HashSet<>();
        Set<EntityId> taunts = new HashSet<>();
        HashMap<EntityId, EntityId> attackEffectMap = new HashMap<>();
        
        for (EntityId entityId : data.entities(AttackEffectComponent.class)) {
            attackEffectMap.put(data.get(entityId, EffectTriggerEntityComponent.class).entity, entityId);
        }
        
        for (EntityId entityId : boardCards) {
            if(data.get(entityId, OwnerComponent.class).player == currentPlayer) {
                if(!data.has(entityId, AttackCountComponent.class) && (!data.has(entityId, SummonSicknessComponent.class) || data.has(entityId, ChargeComponent.class))) {
                    EntityId attackEffect = attackEffectMap.get(entityId);
                    if(attackEffect != null && data.has(entityId, AttackComponent.class)) {
                        attackers.add(attackEffect);
                    }
                }
            } else {
                if(data.has(entityId, HealthComponent.class)) {
                    if(data.has(entityId, TauntComponent.class)) {
                        taunts.add(entityId);
                    } else {
                        defenders.add(entityId);
                    }
                }
            }
        }
        if(!taunts.isEmpty()) {
            defenders = taunts;
        }
        
        for (EntityId attacker : attackers) {
            for (EntityId defender : defenders) {
                commands.add(new Command(attacker, defender));
            }
        }
        
//        ownerFilter.setValue(currentPlayer);
//        notOwnerFilter.setValue(currentPlayer);
//        ManaComponent manaComp = data.get(currentPlayer, ManaComponent.class);
//        int mana = manaComp == null? 0: manaComp.mana;
//        for (EntityId minion : handMinions.list(data)) {
//            ManaCostComponent costComp = data.get(minion, ManaCostComponent.class);
//            int cost = costComp == null? 0: costComp.mana;
//            if(mana >= cost) {
//                triggerFilter.setValue(minion);
//                EntityId effect = summonQuery.first(data);
//                commands.add(new Command(effect));
//            }
//        }
    }
}
