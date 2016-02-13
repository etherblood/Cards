package com.etherblood.montecarlotreesearch;

/**
 *
 * @author Philipp
 */
public interface MoveIterator<T> {

    int size();
    void discardMoves(int num);
    T popMove();
}
