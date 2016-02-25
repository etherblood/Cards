package com.etherblood.cardsmatch.cardgame.events.effects.systems.triggers;

import com.etherblood.cardsmatch.cardgame.AbstractMatchSystem;
import com.etherblood.cardsmatch.cardgame.Autowire;
import com.etherblood.cardsmatch.cardgame.components.effects.EffectTriggerEntityComponent;
import com.etherblood.cardsmatch.cardgame.components.effects.effects.targeting.SelectEffectTargetsComponent;
import com.etherblood.cardsmatch.cardgame.components.misc.OwnerComponent;
import com.etherblood.cardsmatch.cardgame.eventData.EffectTargets;
import com.etherblood.cardsmatch.cardgame.events.effects.TriggerEffectEvent;
import com.etherblood.cardsmatch.cardgame.rng.RngFactory;
import com.etherblood.entitysystem.data.EntityComponentMapReadonly;
import com.etherblood.entitysystem.data.EntityId;
import java.util.List;

/**
 *
 * @author Philipp
 */
public class SelectTargetsEffectSystem extends AbstractMatchSystem<TriggerEffectEvent> {
    @Autowire
    private EntityComponentMapReadonly data;
    @Autowire
    private RngFactory rng;
    
    @Override
    public TriggerEffectEvent handle(TriggerEffectEvent event) {
        SelectEffectTargetsComponent selectComponent = data.get(event.effect, SelectEffectTargetsComponent.class);
        if(selectComponent != null) {
            EntityId trigger = data.get(event.effect, EffectTriggerEntityComponent.class).entity;
            EntityId owner = data.get(trigger, OwnerComponent.class).player;
            List<EntityId> list = selectComponent.filter.select(data, trigger, owner, rng);
            EntityId[] targets = list.toArray(new EntityId[list.size()]);
            eventData().push(new EffectTargets(targets));
        }
        return event;
    }

}
