/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.etherblood.cardsmatch.cardgame.components.effects.conditions;

import com.etherblood.cardsmatch.cardgame.components.effects.effects.util.Condition;
import com.etherblood.entitysystem.data.EntityComponent;

/**
 *
 * @author Philipp
 */
public class TriggerConditionEffectComponent implements EntityComponent {
    public final Condition condition;

    public TriggerConditionEffectComponent(Condition condition) {
        this.condition = condition;
    }
}
