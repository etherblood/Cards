package com.etherblood.cardsmatch.cardgame.events.startTurn.systems;

import com.etherblood.cardsmatch.cardgame.AbstractMatchSystem;
import com.etherblood.match.Autowire;
import com.etherblood.cardsmatch.cardgame.components.battle.buffs.SummonSicknessComponent;
import com.etherblood.cardsmatch.cardgame.events.startTurn.RemoveSummonSicknessEvent;
import com.etherblood.entitysystem.data.EntityComponentMap;

/**
 *
 * @author Philipp
 */
public class RemoveSummonSicknessSystem extends AbstractMatchSystem<RemoveSummonSicknessEvent> {

    @Autowire
    private EntityComponentMap data;

    @Override
    public RemoveSummonSicknessEvent handle(RemoveSummonSicknessEvent event) {
        data.remove(event.target, SummonSicknessComponent.class);
        return event;
    }
}
