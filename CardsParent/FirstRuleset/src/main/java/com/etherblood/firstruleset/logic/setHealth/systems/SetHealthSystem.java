/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.etherblood.firstruleset.logic.setHealth.systems;

import com.etherblood.cardsmatch.cardgame.AbstractMatchSystem;
import com.etherblood.cardscontext.Autowire;
import com.etherblood.firstruleset.logic.battle.stats.HealthComponent;
import com.etherblood.firstruleset.logic.setHealth.SetHealthEvent;
import com.etherblood.entitysystem.data.EntityComponentMap;

/**
 *
 * @author Philipp
 */
public class SetHealthSystem extends AbstractMatchSystem<SetHealthEvent> {
    @Autowire
    private EntityComponentMap data;

    @Override
    public SetHealthEvent handle(SetHealthEvent event) {
        data.set(event.entity, new HealthComponent(event.health));
        return event;
    }
    
}
