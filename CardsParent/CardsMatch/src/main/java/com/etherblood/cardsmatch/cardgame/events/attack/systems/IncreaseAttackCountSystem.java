package com.etherblood.cardsmatch.cardgame.events.attack.systems;

import com.etherblood.cardsmatch.cardgame.AbstractMatchSystem;
import com.etherblood.match.Autowire;
import com.etherblood.cardsmatch.cardgame.components.battle.buffs.AttackCountComponent;
import com.etherblood.cardsmatch.cardgame.events.attack.AttackEvent;
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
