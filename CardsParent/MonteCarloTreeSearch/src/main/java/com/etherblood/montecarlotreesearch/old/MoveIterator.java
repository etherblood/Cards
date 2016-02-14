package com.etherblood.montecarlotreesearch.old;

/**
 *
 * @author Philipp
 */
public interface MoveIterator<T> {

    int size();
    void discardMoves(int num);
    T popMove();
}
