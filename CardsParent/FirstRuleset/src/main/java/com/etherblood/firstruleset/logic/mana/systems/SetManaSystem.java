/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.etherblood.firstruleset.logic.mana.systems;

import com.etherblood.cardsmatch.cardgame.AbstractMatchSystem;
import com.etherblood.cardsmatch.cardgame.match.Autowire;
import com.etherblood.firstruleset.logic.player.ManaComponent;
import com.etherblood.firstruleset.logic.mana.SetManaEvent;
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
