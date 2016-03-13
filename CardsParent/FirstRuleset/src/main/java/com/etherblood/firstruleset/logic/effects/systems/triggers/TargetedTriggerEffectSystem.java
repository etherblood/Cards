package com.etherblood.firstruleset.logic.effects.systems.triggers;

import com.etherblood.cardsmatch.cardgame.AbstractMatchSystem;
import com.etherblood.firstruleset.eventData.EffectTargets;
import com.etherblood.firstruleset.logic.effects.TargetedTriggerEffectEvent;
import com.etherblood.firstruleset.logic.effects.TriggerEffectEvent;

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
