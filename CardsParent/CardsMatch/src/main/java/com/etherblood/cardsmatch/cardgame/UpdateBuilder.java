package com.etherblood.cardsmatch.cardgame;

import com.etherblood.entitysystem.data.EntityComponentMapReadonly;
import com.etherblood.eventsystem.GameEvent;

/**
 *
 * @author Philipp
 */
public interface UpdateBuilder<U, T extends GameEvent> {
    U build(EntityComponentMapReadonly data, IdConverter converter, T event);
}
