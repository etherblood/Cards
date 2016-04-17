/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.etherblood.firstruleset.logic.damage.systems;

import com.etherblood.cardsmatch.cardgame.AbstractMatchSystem;
import com.etherblood.cardscontext.Autowire;
import com.etherblood.firstruleset.logic.battle.stats.HealthComponent;
import com.etherblood.firstruleset.logic.damage.DamageEvent;
import com.etherblood.firstruleset.logic.setHealth.SetHealthEvent;
import com.etherblood.entitysystem.data.EntityComponentMapReadonly;

/**
 *
 * @author Philipp
 */
public class ApplyDamageSystem extends AbstractMatchSystem<DamageEvent> {

    @Autowire
    private EntityComponentMapReadonly data;

    @Override
    public DamageEvent handle(DamageEvent event) {
        HealthComponent health = data.get(event.target, HealthComponent.class);
        if(health.health <= 0) {
            return null;
        }
        enqueueEvent(new SetHealthEvent(event.target, health.health - event.damage));
        return event;
    }

}
