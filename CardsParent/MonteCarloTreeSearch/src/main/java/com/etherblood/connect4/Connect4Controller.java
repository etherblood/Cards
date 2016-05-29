package com.etherblood.connect4;

import com.etherblood.montecarlotreesearch.MonteCarloState;
import com.etherblood.montecarlotreesearch.MonteCarloControls;
import java.util.Random;

/**
 *
 * @author Philipp
 */
public class Connect4Controller {
    private static final int MATCH_RESULT_DRAW = 2;
    private static final int MATCH_RESULT_ONGOING = 3;

    private final Random rng = new Random();
    private final MonteCarloControls controls = new MonteCarloControls();
    private final Connect4 state = new Connect4();
    private final Connect4 simulationState = new Connect4();

    public void reset() {
        state.reset();
    }
    
    public int getVictor() {
        if(state.opponentWon()) {
            return state.opponent();
        } else if(state.isBoardFull()) {
            return MATCH_RESULT_DRAW;
        }
        return MATCH_RESULT_ONGOING;
    }
    
    public void move(int index) {
        long moves = state.generateTokenMoves();
        int numMoves = Long.bitCount(moves);
        long move = extractMoveToken(moves, index);
        state.tokenMove(move);
        controls.move(index, numMoves);
    }
    
    public int think(long millis) {
        millis += System.currentTimeMillis();
        while(System.currentTimeMillis() < millis) {
            iteration();
        }
        long moves = state.generateTokenMoves();
        int numMoves = Long.bitCount(moves);
        int currentPlayer = state.currentPlayer();
        return controls.selectMove(numMoves, currentPlayer);
    }

    private void iteration() {
        simulationState.copyStateFrom(state);
        controls.startIteration();

        while (controls.state() == MonteCarloState.SELECT) {
            long moves = simulationState.generateTokenMoves();
            int numMoves = Long.bitCount(moves);
            int currentPlayer = simulationState.currentPlayer();
            int index = controls.selectMove(numMoves, currentPlayer);
            long move = extractMoveToken(moves, index);
            controls.move(index, numMoves);
            simulationState.tokenMove(move);

            if (simulationState.opponentWon()) {
                controls.declareVictor(simulationState.opponent());
            } else if (simulationState.isBoardFull()) {
                controls.declareVictor(MATCH_RESULT_DRAW);
            }
        }
        if(controls.state() == MonteCarloState.PLAYOUT) {
            controls.declareVictor(playout());
        }
        controls.endIteration();
    }

    public int randomPlayout() {
        simulationState.copyStateFrom(state);
        return playout();
    }
    
    private int playout() {
        while (true) {
            randomMove(simulationState);
            if (simulationState.opponentWon()) {
                return simulationState.opponent();
            } else if (simulationState.isBoardFull()) {
                return MATCH_RESULT_DRAW;
            }
        }
    }
    
    public void randomMove() {
        randomMove(state);
    }

    private void randomMove(Connect4 state) {
        long moves = state.generateTokenMoves();
        int numMoves = Long.bitCount(moves);
        int index = rng.nextInt(numMoves);
        long move = extractMoveToken(moves, index);
        state.tokenMove(move);
    }

    private long extractMoveToken(long moves, int index) {
        for (int i = 0; i < index; i++) {
            moves &= moves - 1;
        }
        return Long.lowestOneBit(moves);
    }

    public Connect4 getState() {
        return state;
    }
}
