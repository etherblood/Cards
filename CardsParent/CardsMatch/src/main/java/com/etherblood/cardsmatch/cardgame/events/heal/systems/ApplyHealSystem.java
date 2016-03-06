/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.etherblood.cardsmatch.cardgame.events.heal.systems;

import com.etherblood.cardsmatch.cardgame.AbstractMatchSystem;
import com.etherblood.match.Autowire;
import com.etherblood.cardsmatch.cardgame.components.battle.stats.HealthComponent;
import com.etherblood.cardsmatch.cardgame.events.heal.HealEvent;
import com.etherblood.cardsmatch.cardgame.events.setHealth.SetHealthEvent;
import com.etherblood.entitysystem.data.EntityComponentMapReadonly;

/**
 *
 * @author Philipp
 */
public class ApplyHealSystem extends AbstractMatchSystem<HealEvent> {
    @Autowire
    private EntityComponentMapReadonly data;

    @Override
    public HealEvent handle(HealEvent event) {
        HealthComponent health = data.get(event.target, HealthComponent.class);
        if(health != null) {
            enqueueEvent(new SetHealthEvent(event.target, health.health + event.heal));
        }
        return event;
    }
    
}
