/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.etherblood.firstruleset.logic.summon.systems;

import com.etherblood.cardsmatch.cardgame.AbstractMatchSystem;
import com.etherblood.cardsmatch.cardgame.match.Autowire;
import com.etherblood.firstruleset.logic.battle.MinionComponent;
import com.etherblood.firstruleset.logic.cards.SpellComponent;
import com.etherblood.firstruleset.logic.cardZones.events.CardZone;
import com.etherblood.firstruleset.logic.cardZones.events.CardZoneMoveEvent;
import com.etherblood.firstruleset.logic.summon.SummonEvent;
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
