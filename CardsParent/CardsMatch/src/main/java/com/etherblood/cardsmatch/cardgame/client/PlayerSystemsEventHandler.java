/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.etherblood.cardsmatch.cardgame.client;

import com.etherblood.entitysystem.data.EntityId;
import com.etherblood.eventsystem.GameEvent;

/**
 *
 * @author Philipp
 */
public interface PlayerSystemsEventHandler {
    void onEvent(EntityId playerId, Class systemClass, GameEvent gameEvent);
}
