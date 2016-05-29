package com.etherblood.firstruleset.logic.attack.systems;

import com.etherblood.cardsmatch.cardgame.AbstractMatchSystem;
import com.etherblood.cardscontext.Autowire;
import com.etherblood.firstruleset.logic.battle.stats.AttackComponent;
import com.etherblood.firstruleset.logic.attack.AttackEvent;
import com.etherblood.firstruleset.logic.damage.DamageEvent;
import com.etherblood.entitysystem.data.EntityComponentMapReadonly;
import com.etherblood.eventsystem.GameEvent;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Philipp
 */
public class ApplyAttackSystem extends AbstractMatchSystem<AttackEvent> {
    @Autowire
    private EntityComponentMapReadonly data;
    
    @Override
    public AttackEvent handle(AttackEvent event) {
        List<GameEvent> damageEvents = new ArrayList<>();
        AttackComponent attack = data.get(event.source, AttackComponent.class);
        if(attack != null && attack.attack > 0) {
            damageEvents.add(new DamageEvent(event.source, event.target, attack.attack));
            enqueueEvent(new DamageEvent(event.source, event.target, attack.attack));
        }
        attack = data.get(event.target, AttackComponent.class);
        if(attack != null && attack.attack > 0) {
            damageEvents.add(new DamageEvent(event.target, event.source, attack.attack));
        }
        enqueueEvents(damageEvents);
        return event;
    }
    
}
