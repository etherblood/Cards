/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.etherblood.firstruleset.logic.effects;

import com.etherblood.entitysystem.data.EntityId;
import com.etherblood.eventsystem.GameEvent;

/**
 *
 * @author Philipp
 */
public class EffectEvent implements GameEvent {
    public final EntityId effect;

    public EffectEvent(EntityId effect) {
        this.effect = effect;
    }
}
