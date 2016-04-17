package com.etherblood.firstruleset.logic.templates.patron.systems;

import com.etherblood.cardsmatch.cardgame.AbstractMatchSystem;
import com.etherblood.cardscontext.Autowire;
import com.etherblood.entitysystem.data.EntityComponentMapReadonly;
import com.etherblood.firstruleset.logic.damage.DamageEvent;
import com.etherblood.firstruleset.logic.templates.patron.PatronAbilityComponent;
import com.etherblood.firstruleset.logic.templates.patron.PatronSurvivalCheckEvent;

/**
 *
 * @author Philipp
 */
public class PatronDamageSystem extends AbstractMatchSystem<DamageEvent> {
    @Autowire
    private EntityComponentMapReadonly data;

    @Override
    public DamageEvent handle(DamageEvent event) {
        if(data.has(event.target, PatronAbilityComponent.class)) {
            enqueueEvent(new PatronSurvivalCheckEvent(event.target));
        }
        return event;
    }

}
