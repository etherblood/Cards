/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.etherblood.cardsmatch.cardgame.events.damage.systems;

import com.etherblood.cardsmatch.cardgame.AbstractMatchSystem;
import com.etherblood.match.Autowire;
import com.etherblood.cardsmatch.cardgame.components.battle.stats.HealthComponent;
import com.etherblood.cardsmatch.cardgame.events.damage.DamageEvent;
import com.etherblood.cardsmatch.cardgame.events.setHealth.SetHealthEvent;
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
        if(health != null) {
            enqueueEvent(new SetHealthEvent(event.target, health.health - event.damage));
        }
        return event;
    }
    
}
