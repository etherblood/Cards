/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.etherblood.cardsmatch.cardgame.events.mana.systems;

import com.etherblood.cardsmatch.cardgame.AbstractMatchSystem;
import com.etherblood.cardsmatch.cardgame.Autowire;
import com.etherblood.cardsmatch.cardgame.components.player.ManaLimitComponent;
import com.etherblood.cardsmatch.cardgame.events.mana.SetManaLimitEvent;
import com.etherblood.entitysystem.data.EntityComponentMap;

/**
 *
 * @author Philipp
 */
public class SetManaLimitSystem extends AbstractMatchSystem<SetManaLimitEvent> {
    @Autowire
    private EntityComponentMap data;

    @Override
    public SetManaLimitEvent handle(SetManaLimitEvent event) {
        data.set(event.entity, new ManaLimitComponent(event.mana));
        return event;
    }
    
}
