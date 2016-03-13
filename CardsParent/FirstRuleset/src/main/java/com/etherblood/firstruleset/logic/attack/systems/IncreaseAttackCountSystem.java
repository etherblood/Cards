package com.etherblood.firstruleset.logic.attack.systems;

import com.etherblood.cardsmatch.cardgame.AbstractMatchSystem;
import com.etherblood.cardsmatch.cardgame.match.Autowire;
import com.etherblood.firstruleset.logic.battle.buffs.AttackCountComponent;
import com.etherblood.firstruleset.logic.attack.AttackEvent;
import com.etherblood.entitysystem.data.EntityComponentMap;

/**
 *
 * @author Philipp
 */
public class IncreaseAttackCountSystem extends AbstractMatchSystem<AttackEvent> {
    @Autowire
    private EntityComponentMap data;

    @Override
    public AttackEvent handle(AttackEvent event) {
        AttackCountComponent attackCountComponent = data.get(event.source, AttackCountComponent.class);
        int attackCount = attackCountComponent == null? 0: attackCountComponent.numAttacks;
        data.set(event.source, new AttackCountComponent(attackCount + 1));
        return event;
    }

}
