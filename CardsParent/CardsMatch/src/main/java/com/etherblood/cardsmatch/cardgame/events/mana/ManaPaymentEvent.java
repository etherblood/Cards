/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.etherblood.cardsmatch.cardgame.events.mana;

import com.etherblood.entitysystem.data.EntityId;
import com.etherblood.eventsystem.GameEvent;

/**
 *
 * @author Philipp
 */
public class ManaPaymentEvent implements GameEvent {
    public final EntityId player;
    public final int mana;
    public final GameEvent spawnEvent;

    public ManaPaymentEvent(EntityId entity, int mana, GameEvent product) {
        this.player = entity;
        this.mana = mana;
        this.spawnEvent = product;
    }
}
