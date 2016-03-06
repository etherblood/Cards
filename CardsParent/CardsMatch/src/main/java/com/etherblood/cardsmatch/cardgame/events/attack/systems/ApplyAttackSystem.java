/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.etherblood.cardsmatch.cardgame.events.attack.systems;

import com.etherblood.cardsmatch.cardgame.AbstractMatchSystem;
import com.etherblood.match.Autowire;
import com.etherblood.cardsmatch.cardgame.components.battle.stats.AttackComponent;
import com.etherblood.cardsmatch.cardgame.events.attack.AttackEvent;
import com.etherblood.cardsmatch.cardgame.events.damage.DamageEvent;
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
