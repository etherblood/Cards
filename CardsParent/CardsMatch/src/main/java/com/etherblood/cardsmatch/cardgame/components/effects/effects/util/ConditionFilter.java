/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.etherblood.cardsmatch.cardgame.components.effects.effects.util;

import com.etherblood.entitysystem.data.EntityComponentMapReadonly;
import com.etherblood.entitysystem.data.EntityId;

/**
 *
 * @author Philipp
 */
public interface ConditionFilter {
    boolean pass(EntityComponentMapReadonly data, EntityId effectSource, EntityId owner, EntityId... targets);
}
