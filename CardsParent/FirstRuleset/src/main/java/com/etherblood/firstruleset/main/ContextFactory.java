package com.etherblood.firstruleset.main;

import com.etherblood.cardscontext.CardsContext;

/**
 *
 * @author Philipp
 */
public interface ContextFactory {
    CardsContext buildContext(Object... extraBeans);
}
