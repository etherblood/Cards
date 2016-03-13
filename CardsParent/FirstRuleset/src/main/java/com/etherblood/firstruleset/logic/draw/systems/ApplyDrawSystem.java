/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.etherblood.firstruleset.logic.draw.systems;

import com.etherblood.cardsmatch.cardgame.AbstractMatchSystem;
import com.etherblood.firstruleset.logic.cardZones.events.CardZone;
import com.etherblood.firstruleset.logic.cardZones.events.CardZoneMoveEvent;
import com.etherblood.firstruleset.logic.draw.DrawEvent;

/**
 *
 * @author Philipp
 */
public class ApplyDrawSystem extends AbstractMatchSystem<DrawEvent> {

    @Override
    public DrawEvent handle(DrawEvent event) {
        enqueueEvent(new CardZoneMoveEvent(event.card, CardZone.LIBRARY, CardZone.HAND));
        return event;
    }
}
