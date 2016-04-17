/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.etherblood.firstruleset.logic.heal.systems;

import com.etherblood.cardsmatch.cardgame.AbstractMatchSystem;
import com.etherblood.cardscontext.Autowire;
import com.etherblood.firstruleset.logic.battle.stats.HealthComponent;
import com.etherblood.firstruleset.logic.heal.HealEvent;
import com.etherblood.firstruleset.logic.setHealth.SetHealthEvent;
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
