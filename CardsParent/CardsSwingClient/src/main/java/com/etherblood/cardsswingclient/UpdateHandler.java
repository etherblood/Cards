package com.etherblood.cardsswingclient;

import com.etherblood.cardsnetworkshared.match.misc.MatchUpdate;

/**
 *
 * @author Philipp
 */
public interface UpdateHandler<T extends MatchUpdate> {
    void handle(T update);
}
