/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.etherblood.firstruleset.logic.summon;

import com.etherblood.eventsystem.GameEvent;
import com.etherblood.entitysystem.data.EntityId;

/**
 *
 * @author Philipp
 */
public class SummonEvent implements GameEvent {
    public final EntityId minion;

    public SummonEvent(EntityId source) {
        this.minion = source;
    }
}
