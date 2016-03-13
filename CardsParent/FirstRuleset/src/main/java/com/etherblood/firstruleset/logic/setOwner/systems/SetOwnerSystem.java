/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.etherblood.firstruleset.logic.setOwner.systems;

import com.etherblood.cardsmatch.cardgame.AbstractMatchSystem;
import com.etherblood.cardsmatch.cardgame.match.Autowire;
import com.etherblood.firstruleset.logic.player.OwnerComponent;
import com.etherblood.firstruleset.logic.setOwner.SetOwnerEvent;
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
