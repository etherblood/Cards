/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.etherblood.cardsmatch.cardgame.events.draw.systems;

import com.etherblood.cardsmatch.cardgame.AbstractMatchSystem;
import com.etherblood.cardsmatch.cardgame.events.cardZones.CardZone;
import com.etherblood.cardsmatch.cardgame.events.cardZones.CardZoneMoveEvent;
import com.etherblood.cardsmatch.cardgame.events.draw.DrawEvent;

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
