/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.etherblood.firstruleset.logic.mana.systems;

import com.etherblood.cardsmatch.cardgame.AbstractMatchSystem;
import com.etherblood.cardscontext.Autowire;
import com.etherblood.firstruleset.logic.player.ManaComponent;
import com.etherblood.firstruleset.logic.mana.ManaPaymentEvent;
import com.etherblood.firstruleset.logic.mana.SetManaEvent;
import com.etherblood.entitysystem.data.EntityComponentMapReadonly;

/**
 *
 * @author Philipp
 */
public class ManaPaymentSystem extends AbstractMatchSystem<ManaPaymentEvent> {
    @Autowire
    private EntityComponentMapReadonly data;

    @Override
    public ManaPaymentEvent handle(ManaPaymentEvent event) {
        ManaComponent manaComp = data.get(event.player, ManaComponent.class);
        int mana = manaComp == null? 0: manaComp.mana;
        mana -= event.mana;
        if(mana < 0) {
            return null;
        }
        enqueueEvent(new SetManaEvent(event.player, mana));
        enqueueEvent(event.spawnEvent);
        return event;
    }
    
}
