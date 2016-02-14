package com.etherblood.connect4;

import com.etherblood.montecarlotreesearch.MonteCarloState;
import com.etherblood.montecarlotreesearch.MonteCarloControls;
import java.util.Random;

/**
 *
 * @author Philipp
 */
public class Connect4Controller {

    private final Random rng = new Random();
    private final MonteCarloControls controls = new MonteCarloControls();
    private final Connect4 state = new Connect4();
    private final Connect4 simulationState = new Connect4();

    public void reset() {
        state.reset();
    }
    
    public int getVictor() {
        if(state.isVictor(0)) {
            return 0;
        } else if(state.isVictor(1)) {
            return 1;
        } else if(state.isBoardFull()) {
            return 2;
        }
        return 3;
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

            assert !simulationState.isVictor(simulationState.currentPlayer());
            if (simulationState.isVictor(currentPlayer)) {
                controls.declareVictor(currentPlayer);
            } else if (simulationState.isBoardFull()) {
                controls.declareVictor(2);
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
            long moves = simulationState.generateTokenMoves();
            int numMoves = Long.bitCount(moves);
            int currentPlayer = simulationState.currentPlayer();
            int index = rng.nextInt(numMoves);
            long move = extractMoveToken(moves, index);
            simulationState.tokenMove(move);

            if (simulationState.isVictor(currentPlayer)) {
                return currentPlayer;
            } else if (simulationState.isBoardFull()) {
                return 2;
            }
        }
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
