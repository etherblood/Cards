package com.etherblood.montecarlotreesearch.old;

/**
 *
 * @author Philipp
 */
public interface GameSimulation<T> {
    static final int PLAYER_1 = 0;
    static final int PLAYER_2 = 1;
    static final int DRAW = 2;
    static final int ONGOING = 3;
    
    void makeMove(T move);
    void undo(T move);
    MoveIterator<T> generateMoves();
    int getVictoryState();
    int currentPlayer();
}