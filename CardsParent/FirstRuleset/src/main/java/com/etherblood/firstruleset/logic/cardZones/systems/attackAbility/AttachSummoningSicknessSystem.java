package com.etherblood.firstruleset.logic.cardZones.systems.attackAbility;

import com.etherblood.cardsmatch.cardgame.AbstractMatchSystem;
import com.etherblood.cardscontext.Autowire;
import com.etherblood.firstruleset.logic.battle.buffs.SummonSicknessComponent;
import com.etherblood.firstruleset.logic.cardZones.events.BoardAttachEvent;
import com.etherblood.entitysystem.data.EntityComponentMap;

/**
 *
 * @author Philipp
 */
public class AttachSummoningSicknessSystem extends AbstractMatchSystem<BoardAttachEvent> {

    @Autowire
    private EntityComponentMap data;

    @Override
    public BoardAttachEvent handle(BoardAttachEvent event) {
        data.set(event.target, new SummonSicknessComponent());
        return event;
    }
}
