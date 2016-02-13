/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.etherblood.cardsmatch.cardgame.events.summon.systems;

import com.etherblood.cardsmatch.cardgame.AbstractMatchSystem;
import com.etherblood.cardsmatch.cardgame.Autowire;
import com.etherblood.cardsmatch.cardgame.components.battle.MinionComponent;
import com.etherblood.cardsmatch.cardgame.components.cards.SpellComponent;
import com.etherblood.cardsmatch.cardgame.events.cardZones.CardZone;
import com.etherblood.cardsmatch.cardgame.events.cardZones.CardZoneMoveEvent;
import com.etherblood.cardsmatch.cardgame.events.summon.SummonEvent;
import com.etherblood.entitysystem.data.EntityComponentMap;

/**
 *
 * @author Philipp
 */
public class ApplySummonSystem extends AbstractMatchSystem<SummonEvent> {
    @Autowire
    private EntityComponentMap data;

    @Override
    public SummonEvent handle(SummonEvent event) {
        if(data.has(event.minion, MinionComponent.class)) {
            enqueueEvent(new CardZoneMoveEvent(event.minion, CardZone.HAND, CardZone.BOARD));
        } else if(data.has(event.minion, SpellComponent.class)) {
            enqueueEvent(new CardZoneMoveEvent(event.minion, CardZone.HAND, CardZone.GRAVEYARD));
        }
        return event;
    }
    
}
