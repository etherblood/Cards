package com.etherblood.cardsmatch.cardgame.bot;

import com.etherblood.cardsmatch.cardgame.components.battle.MinionComponent;
import com.etherblood.cardsmatch.cardgame.components.battle.hero.HeroComponent;
import com.etherblood.cardsmatch.cardgame.components.battle.stats.AttackComponent;
import com.etherblood.cardsmatch.cardgame.components.battle.stats.HealthComponent;
import com.etherblood.cardsmatch.cardgame.components.cards.cardZone.BoardCardComponent;
import com.etherblood.cardsmatch.cardgame.components.misc.OwnerComponent;
import com.etherblood.entitysystem.data.EntityComponentMapReadonly;
import com.etherblood.entitysystem.data.EntityId;
import com.etherblood.entitysystem.filters.AbstractComponentFieldValueFilter;
import com.etherblood.entitysystem.filters.EqualityOperator;
import com.etherblood.entitysystem.filters.FilterQuery;

/**
 *
 * @author Philipp
 */
public class Evaluation {
    private static final int HIGH_NUMBER = 1000000;
    
    public int evaluate(EntityComponentMapReadonly data, EntityId player, EntityId opponent) {
        return evaluate(data, player) - evaluate(data, opponent);
    }
    
    private AbstractComponentFieldValueFilter ownerFilter = OwnerComponent.createPlayerFilter(new EqualityOperator());
    private FilterQuery playerBoardQuery = new FilterQuery()
            .setBaseClass(BoardCardComponent.class)
            .addComponentFilter(ownerFilter)
            .addComponentClassFilter(MinionComponent.class);
    private FilterQuery heroQuery = new FilterQuery()
            .setBaseClass(HeroComponent.class)
            .addComponentFilter(ownerFilter)
            .addComponentClassFilter(BoardCardComponent.class)
            .addComponentClassFilter(HealthComponent.class);
    
    private int evaluate(EntityComponentMapReadonly data, EntityId player) {
        ownerFilter.setValue(player);
        EntityId hero = heroQuery.first(data);
        if(hero == null) {
            return 0;
        }
        int score = scoreHeroHealth(data.get(hero, HealthComponent.class).health);
        for (EntityId entityId : playerBoardQuery.list(data)) {
            HealthComponent healthComp = data.get(entityId, HealthComponent.class);
            if(healthComp != null) {
                score += healthComp.health * 100;
            }
            AttackComponent attackComp = data.get(entityId, AttackComponent.class);
            if(attackComp != null) {
                score += attackComp.attack * 100;
            }
        }
        return score;
    }
    
    private int scoreHeroHealth(int health) {
        return HIGH_NUMBER + (int)(500 * Math.sqrt(health));
    }
}
