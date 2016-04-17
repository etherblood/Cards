package com.etherblood.firstruleset.logic.effects.systems.triggers;

import com.etherblood.cardsmatch.cardgame.AbstractMatchSystem;
import com.etherblood.cardsmatch.cardgame.IllegalCommandException;
import com.etherblood.cardsmatch.cardgame.components.player.ItsMyTurnComponent;
import com.etherblood.cardscontext.Autowire;
import com.etherblood.entitysystem.data.EntityComponentMapReadonly;
import com.etherblood.firstruleset.eventData.EffectTargets;
import com.etherblood.firstruleset.logic.effects.TargetedTriggerEffectEvent;
import com.etherblood.firstruleset.logic.effects.TriggerEffectEvent;
import com.etherblood.firstruleset.logic.effects.triggers.PlayerActivationTriggerComponent;
import com.etherblood.firstruleset.logic.player.OwnerComponent;

/**
 *
 * @author Philipp
 */
public class ValidateTargetedTriggerEffectSystem extends AbstractMatchSystem<TargetedTriggerEffectEvent> {

    @Autowire
    private EntityComponentMapReadonly data;
    
    @Override
    public TargetedTriggerEffectEvent handle(TargetedTriggerEffectEvent event) {
        if(!data.has(event.player, ItsMyTurnComponent.class)) {
            throw new IllegalCommandException("cant execute command because it is not the commandings player turn");
        }
        if(!data.has(event.effect, PlayerActivationTriggerComponent.class)) {
            throw new IllegalCommandException("cant execute command because selected effect is not user-triggered");
        }
        if(!event.player.equals(data.get(event.effect, OwnerComponent.class).player)) {
            throw new IllegalCommandException("cant execute command because selected effect is not owned by commanding player");
        }
        return event;
    }

}
