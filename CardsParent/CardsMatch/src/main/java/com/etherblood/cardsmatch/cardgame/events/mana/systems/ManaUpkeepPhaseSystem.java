/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.etherblood.cardsmatch.cardgame.events.mana.systems;

import com.etherblood.cardsmatch.cardgame.AbstractMatchSystem;
import com.etherblood.match.Autowire;
import com.etherblood.cardsmatch.cardgame.components.player.ManaLimitComponent;
import com.etherblood.cardsmatch.cardgame.events.mana.SetManaEvent;
import com.etherblood.cardsmatch.cardgame.events.mana.SetManaLimitEvent;
import com.etherblood.cardsmatch.cardgame.events.startTurn.StartTurnEvent;
import com.etherblood.entitysystem.data.EntityComponentMapReadonly;

/**
 *
 * @author Philipp
 */
public class ManaUpkeepPhaseSystem extends AbstractMatchSystem<StartTurnEvent> {
    @Autowire
    private EntityComponentMapReadonly data;

    @Override
    public StartTurnEvent handle(StartTurnEvent event) {
        ManaLimitComponent limit = data.get(event.player, ManaLimitComponent.class);
        int manaLimit = limit == null ? 0 : limit.mana;
        manaLimit++;
        enqueueEvent(new SetManaLimitEvent(event.player, manaLimit));
        enqueueEvent(new SetManaEvent(event.player, manaLimit));
        return event;
    }
}
