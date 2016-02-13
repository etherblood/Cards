package com.etherblood.cardsmatch.cardgame.events.effects.systems.conditions;

import com.etherblood.cardsmatch.cardgame.AbstractMatchSystem;
import com.etherblood.cardsmatch.cardgame.Autowire;
import com.etherblood.cardsmatch.cardgame.components.battle.buffs.AttackCountComponent;
import com.etherblood.cardsmatch.cardgame.components.battle.buffs.ChargeComponent;
import com.etherblood.cardsmatch.cardgame.components.battle.buffs.SummonSicknessComponent;
import com.etherblood.cardsmatch.cardgame.components.battle.buffs.TauntComponent;
import com.etherblood.cardsmatch.cardgame.components.cards.cardZone.BoardCardComponent;
import com.etherblood.cardsmatch.cardgame.components.effects.EffectTriggerEntityComponent;
import com.etherblood.cardsmatch.cardgame.components.effects.conditions.CanAttackConditionComponent;
import com.etherblood.cardsmatch.cardgame.components.misc.OwnerComponent;
import com.etherblood.cardsmatch.cardgame.eventData.EffectTargets;
import com.etherblood.cardsmatch.cardgame.events.effects.EffectEvent;
import com.etherblood.entitysystem.data.EntityComponentMapReadonly;
import com.etherblood.entitysystem.data.EntityId;
import com.etherblood.entitysystem.filters.AbstractComponentFieldValueFilter;
import com.etherblood.entitysystem.filters.EqualityOperator;
import com.etherblood.entitysystem.filters.FilterQuery;

/**
 *
 * @author Philipp
 */
public class CanAttackConditionSystem extends AbstractMatchSystem<EffectEvent> {

    @Autowire
    private EntityComponentMapReadonly data;
    private final AbstractComponentFieldValueFilter<OwnerComponent> ownerFilter = OwnerComponent.createPlayerFilter(new EqualityOperator());
    private final FilterQuery opponentTauntsQuery = new FilterQuery()
            .setBaseClass(BoardCardComponent.class)
            .addComponentClassFilter(TauntComponent.class)
            .addComponentFilter(ownerFilter);

    @Override
    public EffectEvent handle(EffectEvent event) {
        if (data.has(event.effect, CanAttackConditionComponent.class)) {
            EntityId attacker = data.get(event.effect, EffectTriggerEntityComponent.class).entity;
            if (data.has(attacker, SummonSicknessComponent.class) && !data.has(attacker, ChargeComponent.class)) {
                System.out.println("can't attack because it has SummonSicknessComponent and no charge");
                return null;
            }
            if (data.has(attacker, AttackCountComponent.class)) {
                System.out.println("can't attack because it has AttackCountComponent");
                return null;
            }
            if(eventData().get(EffectTargets.class).targets.length == 0) {
                System.out.println("WARNING: tried to attack without target");
                return null;
            }
            for (EntityId defender : eventData().get(EffectTargets.class).targets) {
                EntityId opponentPlayer = data.get(defender, OwnerComponent.class).player;
                if (data.get(attacker, OwnerComponent.class).player == opponentPlayer) {
                    return null;
                }
                if (!data.has(defender, TauntComponent.class)) {
                    ownerFilter.setValue(opponentPlayer);
                    if (opponentTauntsQuery.first(data) != null) {
                        return null;
                    }
                }
            }
        }
        return event;
    }
}
