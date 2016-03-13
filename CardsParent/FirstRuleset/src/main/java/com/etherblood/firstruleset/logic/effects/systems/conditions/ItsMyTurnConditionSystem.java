package com.etherblood.firstruleset.logic.effects.systems.conditions;

import com.etherblood.cardsmatch.cardgame.AbstractMatchSystem;
import com.etherblood.cardsmatch.cardgame.match.Autowire;
import com.etherblood.firstruleset.logic.effects.conditions.ItsMyTurnConditionComponent;
import com.etherblood.firstruleset.logic.player.OwnerComponent;
import com.etherblood.cardsmatch.cardgame.components.player.ItsMyTurnComponent;
import com.etherblood.firstruleset.logic.effects.EffectEvent;
import com.etherblood.entitysystem.data.EntityComponentMapReadonly;

/**
 *
 * @author Philipp
 */
public class ItsMyTurnConditionSystem extends AbstractMatchSystem<EffectEvent> {
    @Autowire
    private EntityComponentMapReadonly data;

    @Override
    public EffectEvent handle(EffectEvent event) {
        if(data.has(event.effect, ItsMyTurnConditionComponent.class) && !data.has(data.get(event.effect, OwnerComponent.class).player, ItsMyTurnComponent.class)) {
            return null;
        }
        return event;
    }
}
