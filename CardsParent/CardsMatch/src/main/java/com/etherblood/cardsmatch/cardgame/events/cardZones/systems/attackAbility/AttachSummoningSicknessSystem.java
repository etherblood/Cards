package com.etherblood.cardsmatch.cardgame.events.cardZones.systems.attackAbility;

import com.etherblood.cardsmatch.cardgame.AbstractMatchSystem;
import com.etherblood.cardsmatch.cardgame.Autowire;
import com.etherblood.cardsmatch.cardgame.components.battle.buffs.SummonSicknessComponent;
import com.etherblood.cardsmatch.cardgame.events.cardZones.BoardAttachEvent;
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
