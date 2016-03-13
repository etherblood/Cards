/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.etherblood.firstruleset.logic.damage.systems;

import com.etherblood.cardsmatch.cardgame.AbstractMatchSystem;
import com.etherblood.cardsmatch.cardgame.match.Autowire;
import com.etherblood.firstruleset.logic.battle.buffs.DivineShieldComponent;
import com.etherblood.firstruleset.logic.damage.SetDivineShieldEvent;
import com.etherblood.firstruleset.logic.damage.DamageEvent;
import com.etherblood.entitysystem.data.EntityComponentMap;

/**
 *
 * @author Philipp
 */
public class DivineShieldSystem extends AbstractMatchSystem<DamageEvent> {
    @Autowire
    private EntityComponentMap data;

    @Override
    public DamageEvent handle(DamageEvent event) {
        if(data.has(event.target, DivineShieldComponent.class)) {
            enqueueEvent(new SetDivineShieldEvent(event.target, false));
            return null;
        }
        return event;
    }
    
}
