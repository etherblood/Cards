package com.etherblood.cardsmatch.cardgame.events.effects.systems.triggers;

import com.etherblood.cardsmatch.cardgame.AbstractMatchSystem;
//import com.etherblood.cardsmatch.cardgame.events.effects.ClearEffectTargetsEvent;
import com.etherblood.cardsmatch.cardgame.events.effects.EffectEvent;
import com.etherblood.cardsmatch.cardgame.events.effects.TriggerEffectEvent;

/**
 *
 * @author Philipp
 */
public class TriggerEffectSystem extends AbstractMatchSystem<TriggerEffectEvent> {

    @Override
    public TriggerEffectEvent handle(TriggerEffectEvent event) {
        enqueueEvent(new EffectEvent(event.effect));
//        enqueueEvent(new ClearEffectTargetsEvent(event.effect));
        return event;
    }

}
