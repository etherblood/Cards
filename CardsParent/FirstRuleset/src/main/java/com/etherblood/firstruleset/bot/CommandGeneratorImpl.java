package com.etherblood.firstruleset.bot;

import com.etherblood.cardsmatch.cardgame.EntityUtils;
import com.etherblood.cardsmatchapi.IllegalCommandException;
import com.etherblood.cardsmatch.cardgame.ValidEffectTargetsSelector;
import com.etherblood.cardsmatch.cardgame.bot.commands.Command;
import com.etherblood.firstruleset.bot.monteCarlo.CommandManager;
import com.etherblood.firstruleset.bot.monteCarlo.MoveConsumer;
import com.etherblood.firstruleset.bot.monteCarlo.MoveSelector;
import com.etherblood.firstruleset.logic.battle.buffs.AttackCountComponent;
import com.etherblood.firstruleset.logic.battle.buffs.ChargeComponent;
import com.etherblood.firstruleset.logic.battle.buffs.SummonSicknessComponent;
import com.etherblood.firstruleset.logic.battle.buffs.TauntComponent;
import com.etherblood.firstruleset.logic.battle.stats.AttackComponent;
import com.etherblood.firstruleset.logic.battle.stats.HealthComponent;
import com.etherblood.firstruleset.logic.battle.stats.ManaCostComponent;
import com.etherblood.firstruleset.logic.cardZones.components.BoardCardComponent;
import com.etherblood.firstruleset.logic.cardZones.components.HandCardComponent;
import com.etherblood.firstruleset.logic.effects.EffectTriggerEntityComponent;
import com.etherblood.firstruleset.logic.effects.effects.AttackEffectComponent;
import com.etherblood.firstruleset.logic.effects.effects.EndTurnEffectComponent;
import com.etherblood.firstruleset.logic.effects.effects.SummonEffectComponent;
import com.etherblood.firstruleset.logic.effects.targeting.EffectMinimumTargetsRequiredComponent;
import com.etherblood.firstruleset.logic.effects.targeting.EffectRequiresUserTargetsComponent;
import com.etherblood.cardsmatch.cardgame.components.misc.NameComponent;
import com.etherblood.firstruleset.logic.player.OwnerComponent;
import com.etherblood.cardsmatch.cardgame.components.player.ItsMyTurnComponent;
import com.etherblood.firstruleset.logic.player.ManaComponent;
import com.etherblood.entitysystem.data.EntityComponentMapReadonly;
import com.etherblood.entitysystem.data.EntityId;
import com.etherblood.entitysystem.filters.AbstractComponentFieldValueFilter;
import com.etherblood.entitysystem.filters.EqualityOperator;
import com.etherblood.entitysystem.filters.FilterQuery;
import com.etherblood.entitysystem.util.DeterministicEntityIndices;
import com.etherblood.eventsystem.GameEventQueueImpl;
import com.etherblood.firstruleset.logic.effects.TargetedTriggerEffectEvent;
import com.etherblood.cardscontext.MatchContext;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

/**
 *
 * @author Philipp
 */
public class CommandGeneratorImpl implements CommandManager {
    //TODO: generalize effects so that casting and attacking can be handled identically?

    private final DeterministicEntityIndices indexing = new DeterministicEntityIndices();
    private final AbstractComponentFieldValueFilter<OwnerComponent> ownerFilter = OwnerComponent.createPlayerFilter(new EqualityOperator());
    private final AbstractComponentFieldValueFilter<EffectTriggerEntityComponent> triggerFilter = EffectTriggerEntityComponent.createTriggerFilter(new EqualityOperator());
    private final FilterQuery handCards = new FilterQuery()
            .setBaseClass(HandCardComponent.class)
            .addComponentFilter(ownerFilter);
    private final FilterQuery currentQuery = new FilterQuery()
            .setBaseClass(ItsMyTurnComponent.class);
    private final FilterQuery castEffectQuery = new FilterQuery()
            .setBaseClass(SummonEffectComponent.class)
            .addComponentFilter(triggerFilter);
    private final FilterQuery endTurnQuery = new FilterQuery()
            .setBaseClass(EndTurnEffectComponent.class)
            .addComponentFilter(ownerFilter);

    @Override
    public Command generate(EntityComponentMapReadonly data, ValidEffectTargetsSelector targetSelector, MoveSelector selector) {
        EntityId currentPlayer = currentQuery.first(data);

        ArrayList<EntityId> attackers = attackersList(data, currentPlayer);
        ArrayList<EntityId> castables = castablesList(data, targetSelector, currentPlayer);

        int count = attackers.size() + castables.size() + 1;
        int move = selector.selectMove(count);

        if (move < attackers.size()) {
            EntityId attacker = indexing.getEntityForDeterministicIndex(attackers, move);
            return generateAttack(data, selector, attacker);
        }
        move -= attackers.size();

        if (move < castables.size()) {
            EntityId castable = indexing.getEntityForDeterministicIndex(castables, move);
            return generateCast(data, targetSelector, selector, castable);
        }
        move -= castables.size();

        assert move == 0;
        return generateEndTurn(data);
    }

    private Command generateEndTurn(EntityComponentMapReadonly data) {
        ownerFilter.setValue(currentQuery.first(data));
        return new Command(endTurnQuery.first(data));
    }

    private Command generateCast(EntityComponentMapReadonly data, ValidEffectTargetsSelector targetSelector, MoveSelector selector, EntityId effect) {
        if (data.has(effect, EffectRequiresUserTargetsComponent.class)) {
            //TODO add multi-targeting
            List<EntityId> targets = targetSelector.selectTargets(effect);
            assert !targets.isEmpty();
            int move = selector.selectMove(targets.size());
            EntityId target = indexing.getEntityForDeterministicIndex(targets, move);
            return new Command(effect, target);//only single target selection supported
        } else {
            return new Command(effect);
        }
    }

    private Command generateAttack(EntityComponentMapReadonly data, MoveSelector selector, EntityId attacker) {
        EntityId currentPlayer = currentQuery.first(data);
        ArrayList<EntityId> defenders = defendersList(data, currentPlayer);
        int move = selector.selectMove(defenders.size());
        EntityId defender = indexing.getEntityForDeterministicIndex(defenders, move);
        return new Command(attacker, defender);
    }

    @Override
    public void executeCommand(MatchContext context, Command command) {
        GameEventQueueImpl events = context.getBean(GameEventQueueImpl.class);
        events.fireEvent(new TargetedTriggerEffectEvent(currentQuery.first(context.getBean(EntityComponentMapReadonly.class)), command.effect, command.targets));
        events.handleEvents();
    }

    @Override
    public void validate(EntityComponentMapReadonly data, ValidEffectTargetsSelector targetSelector, EntityId source, EntityId[] targets) {
        EntityId currentPlayer = currentQuery.first(data);
        ArrayList<EntityId> attackersList = attackersList(data, currentPlayer);
        ArrayList<EntityId> castablesList = castablesList(data, targetSelector, currentPlayer);

        if (attackersList.contains(source)) {
            if (targets.length != 1) {
                throw new IllegalCommandException("invalid attacker passed to bot");
            }
            ArrayList<EntityId> defendersList = defendersList(data, currentPlayer);
            if (!defendersList.contains(targets[0])) {
                throw new IllegalCommandException("invalid target passed to bot");
            }
            return;
        }
        if (castablesList.contains(source)) {
            if (data.has(source, EffectRequiresUserTargetsComponent.class)) {
                assert targets.length == 1;//multitarget not supported yet
                List<EntityId> selectTargets = targetSelector.selectTargets(source);
                if (!selectTargets.contains(targets[0])) {
                    throw new IllegalCommandException("invalid target passed to bot");
                }
            } else if (targets.length != 0) {
                System.out.println("WARNING: targets were passed to bot when none were expected");
                System.out.println(EntityUtils.toString(data, targets));
            }
            return;
        }
        ownerFilter.setValue(currentQuery.first(data));
        if (!endTurnQuery.first(data).equals(source)) {
            throw new IllegalCommandException("The selected command was not listed in valid moves of AI");
        }
    }

    @Override
    public void selectCommand(EntityComponentMapReadonly data, ValidEffectTargetsSelector targetSelector, EntityId source, EntityId[] targets, MoveConsumer consumer) {
        EntityId currentPlayer = currentQuery.first(data);
        ArrayList<EntityId> attackersList = attackersList(data, currentPlayer);
        ArrayList<EntityId> castablesList = castablesList(data, targetSelector, currentPlayer);

        int count = attackersList.size() + castablesList.size() + 1;
        int index = indexing.getDeterministicIndexForEntity(attackersList, source);
        if (index != -1) {
            assert targets.length == 1;
            consumer.applyMove(index, count);
            ArrayList<EntityId> defendersList = defendersList(data, currentPlayer);
            index = indexing.getDeterministicIndexForEntity(defendersList, targets[0]);
            assert index != -1;
            consumer.applyMove(index, defendersList.size());
            return;
        }
        index = indexing.getDeterministicIndexForEntity(castablesList, source);
        if (index != -1) {
            consumer.applyMove(attackersList.size() + index, count);
            if (data.has(source, EffectRequiresUserTargetsComponent.class)) {
                assert targets.length == 1;//multitarget not supported yet
                List<EntityId> selectTargets = targetSelector.selectTargets(source);
                index = indexing.getDeterministicIndexForEntity(selectTargets, targets[0]);
                assert index != -1;
                consumer.applyMove(index, selectTargets.size());
            }
            return;
        }
        ownerFilter.setValue(currentQuery.first(data));
        assert endTurnQuery.first(data).equals(source);
        consumer.applyMove(count - 1, count);
    }

    private ArrayList<EntityId> attackersList(EntityComponentMapReadonly data, EntityId currentPlayer) {
        HashMap<EntityId, EntityId> attackEffectMap = new HashMap<>();

        for (EntityId entityId : data.entities(AttackEffectComponent.class)) {
            attackEffectMap.put(data.get(entityId, EffectTriggerEntityComponent.class).entity, entityId);
        }
        Set<EntityId> boardCards = data.entities(BoardCardComponent.class);
        ArrayList<EntityId> attackers = new ArrayList<>();
        for (EntityId entityId : boardCards) {
            if (data.get(entityId, OwnerComponent.class).player == currentPlayer) {
                if (!data.has(entityId, AttackCountComponent.class) && (!data.has(entityId, SummonSicknessComponent.class) || data.has(entityId, ChargeComponent.class))) {
                    EntityId attackEffect = attackEffectMap.get(entityId);
                    if (attackEffect != null && data.has(entityId, AttackComponent.class)) {
                        attackers.add(attackEffect);
                    }
                }
            }
        }
        return attackers;
    }

    private ArrayList<EntityId> castablesList(EntityComponentMapReadonly data, ValidEffectTargetsSelector targetSelector, EntityId currentPlayer) {
        ArrayList<EntityId> castables = new ArrayList<>();
        ownerFilter.setValue(currentPlayer);
        ManaComponent manaComp = data.get(currentPlayer, ManaComponent.class);
        int mana = manaComp == null ? 0 : manaComp.mana;
        for (EntityId card : handCards.list(data)) {
            ManaCostComponent costComp = data.get(card, ManaCostComponent.class);
            int cost = costComp == null ? 0 : costComp.mana;
            if (mana >= cost) {
                triggerFilter.setValue(card);
                EntityId effect = castEffectQuery.first(data);
                EffectMinimumTargetsRequiredComponent minimumTargetsComponent = data.get(effect, EffectMinimumTargetsRequiredComponent.class);
                if (minimumTargetsComponent == null || targetSelector.selectTargets(effect).size() >= minimumTargetsComponent.count) {
                    castables.add(effect);
                }
            }
        }
        return castables;
    }

    private ArrayList<EntityId> defendersList(EntityComponentMapReadonly data, EntityId currentPlayer) {
        Set<EntityId> boardCards = data.entities(BoardCardComponent.class);
        ArrayList<EntityId> defenders = new ArrayList<>();
        ArrayList<EntityId> taunts = new ArrayList<>();
        for (EntityId entityId : boardCards) {
            if (data.get(entityId, OwnerComponent.class).player != currentPlayer) {
                if (data.has(entityId, HealthComponent.class)) {
                    if (data.has(entityId, TauntComponent.class)) {
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
        return defenders;
    }
}
