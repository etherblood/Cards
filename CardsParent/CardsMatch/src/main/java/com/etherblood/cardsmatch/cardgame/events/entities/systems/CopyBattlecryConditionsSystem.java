package com.etherblood.cardsmatch.cardgame.events.entities.systems;

import com.etherblood.cardsmatch.cardgame.AbstractMatchSystem;
import com.etherblood.cardsmatch.cardgame.DefaultTemplateSetFactory;
import com.etherblood.cardsmatch.cardgame.components.effects.EffectTriggerEntityComponent;
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
import com.etherblood.cardsmatch.cardgame.components.misc.NameComponent;
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
                    .setBaseClass(BattlecryTriggerComponent.class)
                    .addComponentFilter(triggerFilter)
                    .addComponentClassFilter(EffectMinimumTargetsRequiredComponent.class);
            List<EntityId> list = battlecryQuery.list(data);
            for (EntityId source : list) {
                copyConditionComponents(source, event.target);
            }
        }
        return event;
    }

    private void copyConditionComponents(EntityId source, EntityId dest) {
        for (Class<? extends EntityComponent> class1 : copyComponentClasses) {
            EntityComponent component = data.get(source, class1);
            if (component != null) {
                if (data.set(dest, component) != null) {
                    throw new IllegalStateException(class1.getName() + " of castEffect was overwritten.");
                }
            }
        }
    }
}
