/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.etherblood.cardsmatch.cardgame.events.setHealth.systems;

import com.etherblood.cardsmatch.cardgame.AbstractMatchSystem;
import com.etherblood.match.Autowire;
import com.etherblood.cardsmatch.cardgame.components.battle.stats.HealthComponent;
import com.etherblood.cardsmatch.cardgame.events.death.DeathEvent;
import com.etherblood.cardsmatch.cardgame.events.setHealth.SetHealthEvent;
import com.etherblood.entitysystem.data.EntityComponentMapReadonly;

/**
 *
 * @author Philipp
 */
public class HealthDeathSystem extends AbstractMatchSystem<SetHealthEvent> {
    @Autowire
    private EntityComponentMapReadonly data;

    @Override
    public SetHealthEvent handle(SetHealthEvent event) {
        HealthComponent health = data.get(event.entity, HealthComponent.class);
        if(health != null && health.health < 1) {
            enqueueEvent(new DeathEvent(event.entity));
        }
        return event;
    }
    
}
