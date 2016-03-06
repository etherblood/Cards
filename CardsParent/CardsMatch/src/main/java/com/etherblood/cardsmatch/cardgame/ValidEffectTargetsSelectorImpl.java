package com.etherblood.cardsmatch.cardgame;

import com.etherblood.cardsmatch.cardgame.components.battle.MinionComponent;
import com.etherblood.cardsmatch.cardgame.components.battle.hero.HeroComponent;
import com.etherblood.cardsmatch.cardgame.components.cards.cardZone.BoardCardComponent;
import com.etherblood.cardsmatch.cardgame.components.effects.EffectTriggerEntityComponent;
import com.etherblood.cardsmatch.cardgame.components.effects.targeting.EffectIsTargetedComponent;
import com.etherblood.cardsmatch.cardgame.components.effects.targeting.EffectRequiresUserTargetsComponent;
import com.etherblood.cardsmatch.cardgame.components.effects.targeting.filters.TargetAlliesComponent;
import com.etherblood.cardsmatch.cardgame.components.effects.targeting.filters.TargetBoardComponent;
import com.etherblood.cardsmatch.cardgame.components.effects.targeting.filters.TargetEnemiesComponent;
import com.etherblood.cardsmatch.cardgame.components.effects.targeting.filters.TargetExcludeSelfComponent;
import com.etherblood.cardsmatch.cardgame.components.effects.targeting.filters.TargetHeroesComponent;
import com.etherblood.cardsmatch.cardgame.components.effects.targeting.filters.TargetMinionsComponent;
import com.etherblood.cardsmatch.cardgame.components.effects.targeting.filters.TargetOwnerComponent;
import com.etherblood.cardsmatch.cardgame.components.effects.targeting.filters.TargetPlayersComponent;
import com.etherblood.cardsmatch.cardgame.components.effects.targeting.filters.TargetSelfComponent;
import com.etherblood.cardsmatch.cardgame.components.effects.targeting.EffectTargetsSingleRandomComponent;
import com.etherblood.cardsmatch.cardgame.components.misc.OwnerComponent;
import com.etherblood.cardsmatch.cardgame.components.player.PlayerComponent;
import com.etherblood.cardsmatch.cardgame.rng.RngFactory;
import com.etherblood.entitysystem.data.EntityComponent;
import com.etherblood.entitysystem.data.EntityComponentMapReadonly;
import com.etherblood.entitysystem.data.EntityId;
import com.etherblood.entitysystem.filters.FilterQuery;
import com.etherblood.match.Autowire;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class ValidEffectTargetsSelectorImpl implements ValidEffectTargetsSelector {

    private final LinkedHashMap<Class, Class> classFilterMap;
    @Autowire
    private EntityComponentMapReadonly data;
    @Autowire
    private RngFactory rng;

    public ValidEffectTargetsSelectorImpl() {
        classFilterMap = new LinkedHashMap<>();
        classFilterMap.put(TargetPlayersComponent.class, PlayerComponent.class);
        classFilterMap.put(TargetHeroesComponent.class, HeroComponent.class);
        classFilterMap.put(TargetBoardComponent.class, BoardCardComponent.class);
        classFilterMap.put(TargetMinionsComponent.class, MinionComponent.class);
    }

    @Override
    public List<EntityId> selectTargets(EntityId effect) {
        assert data.has(effect, EffectIsTargetedComponent.class);
        assert !(data.has(effect, EffectRequiresUserTargetsComponent.class) && data.has(effect, EffectTargetsSingleRandomComponent.class));
        if (data.has(effect, TargetSelfComponent.class)) {
            assert !data.has(effect, TargetExcludeSelfComponent.class);
            EntityId self = data.get(effect, EffectTriggerEntityComponent.class).entity;
            return Arrays.asList(self);
        }
        if (data.has(effect, TargetOwnerComponent.class)) {
            EntityId owner = data.get(effect, OwnerComponent.class).player;
            return Arrays.asList(owner);
        }
        
        FilterQuery query = new FilterQuery();
        boolean needsBaseClass = true;
        for (Map.Entry<Class, Class> entry : classFilterMap.entrySet()) {
            if (data.has(effect, entry.getKey())) {
                if (needsBaseClass) {
                    needsBaseClass = false;
                    query.setBaseClass(entry.getValue());
                } else {
                    query.addComponentClassFilter(entry.getValue());
                }
            }
        }
        if (needsBaseClass) {
            throw new IllegalStateException("no baseclass for query found");
        }
        List<EntityId> list = query.list(data);
        allyFilter(effect, list);
        enemyFilter(effect, list);
        notSelfFilter(effect, list);
        randomFilter(effect, list);
        return list;
    }

    private void allyFilter(EntityId effect, List<EntityId> list) {
        if (data.has(effect, TargetAlliesComponent.class)) {
            EntityId owner = data.get(effect, OwnerComponent.class).player;
            for (int i = list.size() - 1; i >= 0; i--) {
                EntityId item = list.get(i);
                OwnerComponent itemOwner = data.get(item, OwnerComponent.class);
                if (itemOwner != null) {
                    if (!owner.equals(itemOwner.player)) {
                        list.remove(i);
                    }
                } else if (!owner.equals(item)) {
                    list.remove(i);
                }
            }
            assert !data.has(effect, TargetEnemiesComponent.class);
        }
    }

    private void enemyFilter(EntityId effect, List<EntityId> list) {
        if (data.has(effect, TargetEnemiesComponent.class)) {
            EntityId owner = data.get(effect, OwnerComponent.class).player;
            for (int i = list.size() - 1; i >= 0; i--) {
                EntityId item = list.get(i);
                OwnerComponent itemOwner = data.get(item, OwnerComponent.class);
                if (itemOwner != null) {
                    if (owner.equals(itemOwner.player)) {
                        list.remove(i);
                    }
                } else if (owner.equals(item)) {
                    list.remove(i);
                }
            }
        }
    }

    private void notSelfFilter(EntityId effect, List<EntityId> list) {
        if (data.has(effect, TargetExcludeSelfComponent.class)) {
            EntityId self = data.get(effect, EffectTriggerEntityComponent.class).entity;
            list.remove(self);
        }
    }

    private void randomFilter(EntityId effect, List<EntityId> list) {
        if (!list.isEmpty() && data.has(effect, EffectTargetsSingleRandomComponent.class)) {
            EntityId randomItem = list.get(rng.nextInt(list.size()));
            list.clear();
            list.add(randomItem);
        }
    }
}