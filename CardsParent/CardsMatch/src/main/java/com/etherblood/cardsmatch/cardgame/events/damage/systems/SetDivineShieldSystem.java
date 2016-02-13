package com.etherblood.cardsmatch.cardgame.events.damage.systems;

import com.etherblood.cardsmatch.cardgame.AbstractMatchSystem;
import com.etherblood.cardsmatch.cardgame.Autowire;
import com.etherblood.cardsmatch.cardgame.components.battle.buffs.DivineShieldComponent;
import com.etherblood.cardsmatch.cardgame.events.damage.SetDivineShieldEvent;
import com.etherblood.entitysystem.data.EntityComponentMap;

/**
 *
 * @author Philipp
 */
public class SetDivineShieldSystem extends AbstractMatchSystem<SetDivineShieldEvent> {

    @Autowire
    private EntityComponentMap data;

    @Override
    public SetDivineShieldEvent handle(SetDivineShieldEvent event) {
        if (event.value) {
            if (data.set(event.target, new DivineShieldComponent()) != null) {
                throw new IllegalStateException();
            }
        } else if (data.remove(event.target, DivineShieldComponent.class) == null) {
            throw new IllegalStateException();
        }
        return event;
    }
}
