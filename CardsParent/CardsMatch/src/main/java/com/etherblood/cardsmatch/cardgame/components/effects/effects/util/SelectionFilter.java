/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.etherblood.cardsmatch.cardgame.components.effects.effects.util;

import com.etherblood.cardsmatch.cardgame.rng.RngFactory;
import com.etherblood.entitysystem.data.EntityComponentMapReadonly;
import com.etherblood.entitysystem.data.EntityId;
import java.util.List;

/**
 *
 * @author Philipp
 */
public interface SelectionFilter {
    List<EntityId> select(EntityComponentMapReadonly data, EntityId effectSource, EntityId owner, RngFactory rng);
}
