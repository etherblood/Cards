package com.etherblood.cardsmatchapi;

import java.util.List;

/**
 *
 * @author Philipp
 */
public interface HumanProxy<A, U> extends PlayerProxy {
    void applyAction(A action);
    List<U> getTotalUpdates();
}
