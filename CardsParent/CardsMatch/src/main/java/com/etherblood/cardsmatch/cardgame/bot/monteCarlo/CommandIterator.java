package com.etherblood.cardsmatch.cardgame.bot.monteCarlo;

import com.etherblood.cardsmatch.cardgame.MatchState;
import com.etherblood.cardsmatch.cardgame.bot.commands.Command;
import com.etherblood.cardsmatch.cardgame.bot.commands.EndTurnCommandFactory;
import com.etherblood.cardsmatch.cardgame.components.battle.buffs.AttackCountComponent;
import com.etherblood.cardsmatch.cardgame.components.battle.buffs.ChargeComponent;
import com.etherblood.cardsmatch.cardgame.components.battle.buffs.SummonSicknessComponent;
import com.etherblood.cardsmatch.cardgame.components.battle.buffs.TauntComponent;
import com.etherblood.cardsmatch.cardgame.components.battle.stats.AttackComponent;
import com.etherblood.cardsmatch.cardgame.components.battle.stats.HealthComponent;
import com.etherblood.cardsmatch.cardgame.components.battle.stats.ManaCostComponent;
import com.etherblood.cardsmatch.cardgame.components.cards.cardZone.BoardCardComponent;
import com.etherblood.cardsmatch.cardgame.components.cards.cardZone.HandCardComponent;
import com.etherblood.cardsmatch.cardgame.components.effects.EffectTriggerEntityComponent;
import com.etherblood.cardsmatch.cardgame.components.effects.effects.AttackEffectComponent;
import com.etherblood.cardsmatch.cardgame.components.effects.effects.EndTurnEffectComponent;
import com.etherblood.cardsmatch.cardgame.components.effects.effects.SummonEffectComponent;
import com.etherblood.cardsmatch.cardgame.components.misc.OwnerComponent;
import com.etherblood.cardsmatch.cardgame.components.player.ItsMyTurnComponent;
import com.etherblood.cardsmatch.cardgame.components.player.ManaComponent;
import com.etherblood.entitysystem.data.EntityId;
import com.etherblood.entitysystem.filters.AbstractComponentFieldValueFilter;
import com.etherblood.entitysystem.filters.EqualityOperator;
import com.etherblood.entitysystem.filters.FilterQuery;
import com.etherblood.montecarlotreesearch.MoveIterator;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 *
 * @author Philipp
 */
public class CommandIterator implements MoveIterator<VersionedCommand> {

    private final MatchState state;
    private final ArrayList<EntityId> attackers = new ArrayList<>();
    private final ArrayList<EntityId> defenders = new ArrayList<>();
    private final ArrayList<EntityId> castables = new ArrayList<>();
    private int pointer;

    public CommandIterator(MatchState state) {
        this.state = state;
    }

    public void clear() {
        pointer = 0;
        attackers.clear();
        defenders.clear();
        castables.clear();
    }

    public void generate() {
        initAttacks();
        initSummons();
    }

    private void initAttacks() {
        EntityId currentPlayer = currentQuery.first(state.data);

        Set<EntityId> boardCards = state.data.entities(BoardCardComponent.class);
        Set<EntityId> attackers = new HashSet<>();
        Set<EntityId> defenders = new HashSet<>();
        Set<EntityId> taunts = new HashSet<>();
        HashMap<EntityId, EntityId> attackEffectMap = new HashMap<>();

        for (EntityId entityId : state.data.entities(AttackEffectComponent.class)) {
            attackEffectMap.put(state.data.get(entityId, EffectTriggerEntityComponent.class).entity, entityId);
        }

        for (EntityId entityId : boardCards) {
            if (state.data.get(entityId, OwnerComponent.class).player == currentPlayer) {
                if (!state.data.has(entityId, AttackCountComponent.class) && (!state.data.has(entityId, SummonSicknessComponent.class) || state.data.has(entityId, ChargeComponent.class))) {
                    EntityId attackEffect = attackEffectMap.get(entityId);
                    if (attackEffect != null && state.data.has(entityId, AttackComponent.class)) {
                        attackers.add(attackEffect);
                    }
                }
            } else {
                if (state.data.has(entityId, HealthComponent.class)) {
                    if (state.data.has(entityId, TauntComponent.class)) {
                        taunts.add(entityId);
                    } else {
                        defenders.add(entityId);
                    }
                }
            }
        }
        if (!taunts.isEmpty()) {
            defenders = taunts;
        }

        this.attackers.addAll(attackers);
        this.defenders.addAll(defenders);
    }
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

    private void initSummons() {
        //TODO AI currently can't select targets
        EntityId currentPlayer = currentQuery.first(state.data);
        ownerFilter.setValue(currentPlayer);
        ManaComponent manaComp = state.data.get(currentPlayer, ManaComponent.class);
        int mana = manaComp == null ? 0 : manaComp.mana;
        for (EntityId minion : handMinions.list(state.data)) {
            ManaCostComponent costComp = state.data.get(minion, ManaCostComponent.class);
            int cost = costComp == null ? 0 : costComp.mana;
            if (mana >= cost) {
                triggerFilter.setValue(minion);
                EntityId effect = summonQuery.first(state.data);
                castables.add(effect);
            }
        }
    }

    @Override
    public int size() {
        return attackers.size() * defenders.size() + castables.size() + 1;
    }

    @Override
    public void discardMoves(int num) {
        pointer += num;
    }

    @Override
    public VersionedCommand popMove() {
        if (pointer == 0) {
            return new VersionedCommand(generateEndTurn());
        } else {
            pointer--;
            if (pointer < castables.size()) {
                Collections.sort(castables);
                return new VersionedCommand(generateCast(castables.get(pointer)));
            } else {
                pointer -= castables.size();
                assert pointer < attackers.size() * defenders.size();
                Collections.sort(attackers);
                Collections.sort(defenders);
                int attacker = pointer % attackers.size();
                int defender = pointer / attackers.size();
                return new VersionedCommand(generateAttack(attackers.get(attacker), defenders.get(defender)));
            }
        }
    }
    private final FilterQuery effectQuery = new FilterQuery()
            .setBaseClass(EndTurnEffectComponent.class)
            .addComponentFilter(ownerFilter);

    private Command generateEndTurn() {
        ownerFilter.setValue(currentQuery.first(state.data));
        return new Command(effectQuery.first(state.data));
    }

    private Command generateCast(EntityId card) {
        return new Command(card);
    }

    private Command generateAttack(EntityId attacker, EntityId defender) {
        return new Command(attacker, defender);
    }
}
