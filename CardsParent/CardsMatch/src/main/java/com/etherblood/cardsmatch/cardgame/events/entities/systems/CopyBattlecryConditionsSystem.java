package com.etherblood.cardsmatch.cardgame.events.entities.systems;

import com.etherblood.cardsmatch.cardgame.AbstractMatchSystem;
import com.etherblood.cardsmatch.cardgame.DefaultTemplateSetFactory;
import com.etherblood.cardsmatch.cardgame.EntityTemplate;
import com.etherblood.cardsmatch.cardgame.TemplateSet;
import com.etherblood.cardsmatch.cardgame.components.effects.EffectTriggerEntityComponent;
import com.etherblood.cardsmatch.cardgame.components.effects.effects.SummonEffectComponent;
import com.etherblood.cardsmatch.cardgame.components.effects.targeting.EffectIsTargetedComponent;
import com.etherblood.cardsmatch.cardgame.components.effects.targeting.EffectMinimumTargetsRequiredComponent;
import com.etherblood.cardsmatch.cardgame.components.effects.targeting.EffectRequiresUserTargetsComponent;
import com.etherblood.cardsmatch.cardgame.components.effects.targeting.EffectTargetsSingleRandomComponent;
import com.etherblood.cardsmatch.cardgame.components.effects.targeting.filters.TargetAlliesComponent;
import com.etherblood.cardsmatch.cardgame.components.effects.targeting.filters.TargetBoardComponent;
import com.etherblood.cardsmatch.cardgame.components.effects.targeting.filters.TargetEnemiesComponent;
import com.etherblood.cardsmatch.cardgame.components.effects.targeting.filters.TargetExcludeSelfComponent;
import com.etherblood.cardsmatch.cardgame.components.effects.targeting.filters.TargetHeroesComponent;
import com.etherblood.cardsmatch.cardgame.components.effects.targeting.filters.TargetMinionsComponent;
import com.etherblood.cardsmatch.cardgame.components.effects.targeting.filters.TargetOwnerComponent;
import com.etherblood.cardsmatch.cardgame.components.effects.targeting.filters.TargetPlayersComponent;
import com.etherblood.cardsmatch.cardgame.components.effects.targeting.filters.TargetSelfComponent;
import com.etherblood.cardsmatch.cardgame.components.effects.triggers.BattlecryTriggerComponent;
import com.etherblood.cardsmatch.cardgame.components.effects.triggers.PlayerActivationTriggerComponent;
import com.etherblood.cardsmatch.cardgame.components.misc.NameComponent;
import com.etherblood.cardsmatch.cardgame.events.cardZones.HandAttachEvent;
import com.etherblood.cardsmatch.cardgame.events.entities.AttachTemplateEvent;
import com.etherblood.entitysystem.data.EntityComponent;
import com.etherblood.entitysystem.data.EntityComponentMap;
import com.etherblood.entitysystem.data.EntityId;
import com.etherblood.entitysystem.filters.AbstractComponentFieldValueFilter;
import com.etherblood.entitysystem.filters.EqualityOperator;
import com.etherblood.entitysystem.filters.FilterQuery;
import com.etherblood.match.Autowire;
import java.util.Arrays;
import java.util.List;

/**
 *
 * @author Philipp
 */
public class CopyBattlecryConditionsSystem extends AbstractMatchSystem<AttachTemplateEvent> {

    @Autowire
    private EntityComponentMap data;
    private List<Class<? extends EntityComponent>> copyComponentClasses = Arrays.asList(
            EffectIsTargetedComponent.class,
            EffectMinimumTargetsRequiredComponent.class,
            EffectRequiresUserTargetsComponent.class,
            EffectTargetsSingleRandomComponent.class,
            TargetAlliesComponent.class,
            TargetBoardComponent.class,
            TargetEnemiesComponent.class,
            TargetExcludeSelfComponent.class,
            TargetHeroesComponent.class,
            TargetMinionsComponent.class,
            TargetOwnerComponent.class,
            TargetPlayersComponent.class,
            TargetSelfComponent.class);

    @Override
    public AttachTemplateEvent handle(AttachTemplateEvent event) {
        if (DefaultTemplateSetFactory.ACTIVATION_SUMMON.equals(event.template)) {
            AbstractComponentFieldValueFilter<EffectTriggerEntityComponent> triggerFilter = EffectTriggerEntityComponent.createTriggerFilter(new EqualityOperator());
            triggerFilter.setValue(event.parent);
            FilterQuery battlecryQuery = new FilterQuery()
                    .setBaseClass(EffectRequiresUserTargetsComponent.class)
                    .addComponentClassFilter(BattlecryTriggerComponent.class)
                    .addComponentFilter(triggerFilter);
            List<EntityId> list = battlecryQuery.list(data);
            if (list.size() == 1) {
                for (EntityId source : list) {
                    copyConditionComponents(source, event.target);
                }
            } else if (!list.isEmpty()) {
                System.out.println("WARNING, skipped attaching following conditions to " + data.get(event.parent, NameComponent.class).name + ":");
                for (EntityId entityId : list) {
                    System.out.println(data.get(entityId, NameComponent.class).name);
                }
            }
        }
        return event;
    }

    private void copyConditionComponents(EntityId source, EntityId dest) {
        for (Class<? extends EntityComponent> class1 : copyComponentClasses) {
            EntityComponent component = data.get(source, class1);
            if (component != null) {
                data.set(dest, component);
            }
        }
    }
}
