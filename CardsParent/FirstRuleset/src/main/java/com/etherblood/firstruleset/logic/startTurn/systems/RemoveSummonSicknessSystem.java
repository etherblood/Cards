package com.etherblood.firstruleset.logic.startTurn.systems;

import com.etherblood.cardsmatch.cardgame.AbstractMatchSystem;
import com.etherblood.cardsmatch.cardgame.match.Autowire;
import com.etherblood.firstruleset.logic.battle.buffs.SummonSicknessComponent;
import com.etherblood.firstruleset.logic.startTurn.RemoveSummonSicknessEvent;
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
