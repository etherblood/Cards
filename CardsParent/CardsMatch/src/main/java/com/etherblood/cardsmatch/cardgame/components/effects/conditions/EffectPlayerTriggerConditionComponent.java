package com.etherblood.cardsmatch.cardgame.components.effects.conditions;

import com.etherblood.cardsmatch.cardgame.components.effects.effects.util.ConditionFilter;
import com.etherblood.entitysystem.data.EntityComponent;

/**
 *
 * @author Philipp
 */
public class EffectPlayerTriggerConditionComponent implements EntityComponent {
    public final ConditionFilter filter;

    public EffectPlayerTriggerConditionComponent(ConditionFilter filter) {
        this.filter = filter;
    }
}
