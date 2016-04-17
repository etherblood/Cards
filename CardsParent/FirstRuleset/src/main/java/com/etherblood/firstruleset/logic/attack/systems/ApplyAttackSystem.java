/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.etherblood.firstruleset.logic.attack.systems;

import com.etherblood.cardsmatch.cardgame.AbstractMatchSystem;
import com.etherblood.cardscontext.Autowire;
import com.etherblood.firstruleset.logic.battle.stats.AttackComponent;
import com.etherblood.firstruleset.logic.attack.AttackEvent;
import com.etherblood.firstruleset.logic.damage.DamageEvent;
import com.etherblood.entitysystem.data.EntityComponentMapReadonly;

/**
 *
 * @author Philipp
 */
public class ApplyAttackSystem extends AbstractMatchSystem<AttackEvent> {
    @Autowire
    private EntityComponentMapReadonly data;
    
    @Override
    public AttackEvent handle(AttackEvent event) {
        AttackComponent attack = data.get(event.source, AttackComponent.class);
        if(attack != null && attack.attack > 0) {
            enqueueEvent(new DamageEvent(event.source, event.target, attack.attack));
        }
        attack = data.get(event.target, AttackComponent.class);
        if(attack != null && attack.attack > 0) {
            enqueueEvent(new DamageEvent(event.target, event.source, attack.attack));
        }
        return event;
    }
    
}
