package com.etherblood.firstruleset.main;

import com.etherblood.cardscontext.MatchContext;

/**
 *
 * @author Philipp
 */
public interface ContextFactory {
    MatchContext buildContext(boolean core);
}
