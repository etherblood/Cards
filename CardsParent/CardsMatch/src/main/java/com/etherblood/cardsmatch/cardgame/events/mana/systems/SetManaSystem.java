/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.etherblood.cardsmatch.cardgame.events.mana.systems;

import com.etherblood.cardsmatch.cardgame.AbstractMatchSystem;
import com.etherblood.cardsmatch.cardgame.Autowire;
import com.etherblood.cardsmatch.cardgame.components.player.ManaComponent;
import com.etherblood.cardsmatch.cardgame.events.mana.SetManaEvent;
import com.etherblood.entitysystem.data.EntityComponentMap;

/**
 *
 * @author Philipp
 */
public class SetManaSystem extends AbstractMatchSystem<SetManaEvent> {
    @Autowire
    private EntityComponentMap data;

    @Override
    public SetManaEvent handle(SetManaEvent event) {
        data.set(event.entity, new ManaComponent(event.mana));
        return event;
    }
    
}
