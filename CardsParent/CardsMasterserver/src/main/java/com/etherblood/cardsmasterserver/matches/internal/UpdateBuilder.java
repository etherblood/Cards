package com.etherblood.cardsmasterserver.matches.internal;

import com.etherblood.cardsmatch.cardgame.MatchState;
import com.etherblood.cardsnetworkshared.match.misc.MatchUpdate;
import com.etherblood.eventsystem.GameEvent;

/**
 *
 * @author Philipp
 */
public interface UpdateBuilder<T extends GameEvent> {
    MatchUpdate build(MatchState match, IdConverter converter, T event);
}
