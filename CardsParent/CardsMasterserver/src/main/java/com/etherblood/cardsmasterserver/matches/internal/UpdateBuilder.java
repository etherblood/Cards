package com.etherblood.cardsmasterserver.matches.internal;

import com.etherblood.cardsnetworkshared.match.misc.MatchUpdate;
import com.etherblood.entitysystem.data.EntityComponentMapReadonly;
import com.etherblood.eventsystem.GameEvent;

/**
 *
 * @author Philipp
 */
public interface UpdateBuilder<T extends GameEvent> {
    MatchUpdate build(EntityComponentMapReadonly data, IdConverter converter, T event);
}
