/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.etherblood.firstruleset.logic.setHealth.systems;

import com.etherblood.cardsmatch.cardgame.AbstractMatchSystem;
import com.etherblood.cardscontext.Autowire;
import com.etherblood.firstruleset.logic.battle.stats.HealthComponent;
import com.etherblood.firstruleset.logic.death.DeathEvent;
import com.etherblood.firstruleset.logic.setHealth.SetHealthEvent;
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
