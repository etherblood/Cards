/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.etherblood.cardsmatch.cardgame.events.setOwner.systems;

import com.etherblood.cardsmatch.cardgame.AbstractMatchSystem;
import com.etherblood.cardsmatch.cardgame.Autowire;
import com.etherblood.cardsmatch.cardgame.components.misc.OwnerComponent;
import com.etherblood.cardsmatch.cardgame.events.setOwner.SetOwnerEvent;
import com.etherblood.entitysystem.data.EntityComponentMap;

/**
 *
 * @author Philipp
 */
public class SetOwnerSystem extends AbstractMatchSystem<SetOwnerEvent> {
    @Autowire
    private EntityComponentMap data;

    @Override
    public SetOwnerEvent handle(SetOwnerEvent event) {
        data.set(event.target, new OwnerComponent(event.owner));
        return event;
    }
    
}
