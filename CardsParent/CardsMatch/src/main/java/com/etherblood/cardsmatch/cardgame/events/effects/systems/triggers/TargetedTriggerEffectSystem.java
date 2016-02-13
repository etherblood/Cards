package com.etherblood.cardsmatch.cardgame.events.effects.systems.triggers;

import com.etherblood.cardsmatch.cardgame.AbstractMatchSystem;
import com.etherblood.cardsmatch.cardgame.eventData.EffectTargets;
import com.etherblood.cardsmatch.cardgame.events.effects.TargetedTriggerEffectEvent;
import com.etherblood.cardsmatch.cardgame.events.effects.TriggerEffectEvent;

/**
 *
 * @author Philipp
 */
public class TargetedTriggerEffectSystem extends AbstractMatchSystem<TargetedTriggerEffectEvent> {

    @Override
    public TargetedTriggerEffectEvent handle(TargetedTriggerEffectEvent event) {
        eventData().push(new EffectTargets(event.targets));
        enqueueEvent(new TriggerEffectEvent(event.effect));
        return event;
    }

}
