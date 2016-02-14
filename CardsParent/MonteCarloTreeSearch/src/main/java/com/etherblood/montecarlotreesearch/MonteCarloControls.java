package com.etherblood.montecarlotreesearch;

import java.util.ArrayList;
import java.util.Random;

/**
 *
 * @author Philipp
 */
public class MonteCarloControls {

    private final static float epsilon = 1e-6f;
    private final static float constant = (float) Math.sqrt(2);
    private final Random rng = new Random();
    private boolean verbose = true;
    private int playoutsResult = -1;
    private MonteCarloNode current = new MonteCarloNode();
    private MonteCarloState state = MonteCarloState.DISABLED;
    private final ArrayList<MonteCarloNode> nodeHistory = new ArrayList<>();

    public void startIteration() {
        assert state == MonteCarloState.DISABLED;
        state = MonteCarloState.SELECT;
        nodeHistory.add(current);
    }

    public void endIteration() {
        apply();
        reset();
    }

    private void apply() {
        assert 0 <= playoutsResult && playoutsResult <= 2;
        assert state == MonteCarloState.APPLY;
        int score0 = (playoutsResult + 2) % 3;
        int score1 = 2 - score0;

        for (MonteCarloNode node : nodeHistory) {
            node.scores[0] += score0;
            node.scores[1] += score1;
        }
        state = MonteCarloState.RESET;
    }

    private void reset() {
        assert state == MonteCarloState.RESET;
        playoutsResult = -1;
        current = nodeHistory.get(0);
        nodeHistory.clear();
        state = MonteCarloState.DISABLED;
    }

    public void declareVictor(int result) {
        assert state == MonteCarloState.SELECT || state == MonteCarloState.PLAYOUT;
        assert playoutsResult == -1;
        playoutsResult = result;
        state = MonteCarloState.APPLY;
    }

    public int selectMove(int moveCount, int player) {
        switch (state) {
            case DISABLED:
                if (current.isLeaf()) {
                    current.initChilds(moveCount);
                }
                return bestChild(player);
            case SELECT:
                if (current.isLeaf()) {
                    current.initChilds(moveCount);
                    state = MonteCarloState.PLAYOUT;
                }
                return selectChild(player);
            default:
                throw new AssertionError(state);
        }
    }

    private int bestChild(int player) {
        int best = 0;
        for (int i = 1; i < current.numChilds(); i++) {
            if (current.getChild(i).visitScore() > current.getChild(best).visitScore()) {
                best = i;
            }
        }
        if (verbose) {
            String scores = "";
            for (int x = 0; x < current.numChilds(); x++) {
                scores += ", ";
                if (x == best) {
                    scores += "{";
                }
                float winrate = current.getChild(x).winrate(player);
                assert 0 <= winrate && winrate <= 1;
                scores += ((int) (winrate * 100)) + "%";
                if (x == best) {
                    scores += "}";
                }
            }
            scores = scores.replaceFirst(", ", "");
            System.out.println("simulation-strength: " + current.visitScore() / 2);
            System.out.println(scores);
        }
        return best;
    }

    public void move(int move, int moveCount) {
        switch (state) {
            case SELECT:
            case PLAYOUT:
                assert current.numChilds() == moveCount;
                current = current.getChild(move);
                nodeHistory.add(current);
                break;
            case DISABLED:
                if (current.isLeaf()) {
                    current = new MonteCarloNode();
                } else {
                    assert current.numChilds() == moveCount;
                    current = current.getChild(move);
                }
                break;
            default:
                throw new AssertionError(state);
        }
    }

    private int selectChild(int player) {
        int best = -1;
        double bestValue = Double.NEGATIVE_INFINITY;
        for (int i = 0; i < current.numChilds(); i++) {
            MonteCarloNode child = current.getChild(i);
            float uctValue = calcUtc(current.visitScore(), child.visitScore(), child.playerScore(player));
            uctValue += epsilon * rng.nextFloat();
            if (uctValue > bestValue) {
                bestValue = uctValue;
                best = i;
            }
        }
        assert 0 <= best && best < current.numChilds();
        return best;
    }

    private float calcUtc(float totalScore, float childTotal, float childScore) {
        childTotal += epsilon;
        totalScore += 1;
        float exploitation = childScore / childTotal;
        float exploration = constant * (float) (Math.sqrt(Math.log(totalScore) / childTotal));
        float uctValue = exploitation + exploration;
        return uctValue;
    }

    public void setVerbose(boolean verbose) {
        this.verbose = verbose;
    }

    public MonteCarloState state() {
        return state;
    }
}
