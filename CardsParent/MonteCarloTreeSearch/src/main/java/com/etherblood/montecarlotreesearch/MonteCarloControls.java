package com.etherblood.montecarlotreesearch;

import static com.etherblood.montecarlotreesearch.MonteCarloState.DISABLED;
import static com.etherblood.montecarlotreesearch.MonteCarloState.PLAYOUT;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

/**
 *
 * @author Philipp
 */
public class MonteCarloControls {
    public static final int PLAYER_1 = 0;
    public static final int PLAYER_2 = 1;
    public static final int DRAW = 2;
    public static final int ONGOING = 3;

    private final static float EPSILON = 1e-6f;
    private final static float CONSTANT = (float) Math.sqrt(2);
    private final Random rng = new Random();
    private boolean verbose = true;
    private int playoutsResult = -1;
    private MonteCarloNode current = new MonteCarloNode();
    private MonteCarloState state = MonteCarloState.DISABLED;
    private final ArrayList<MonteCarloNode> nodeHistory = new ArrayList<>();

    public void clear() {
        current = new MonteCarloNode();
        assert nodeHistory.isEmpty();
        assert state == MonteCarloState.DISABLED;
    }
    
    public void startIteration() {
        assert state == MonteCarloState.DISABLED;
        state = MonteCarloState.SELECT;
        nodeHistory.add(current);
    }

    public void endIteration() {
        apply();
        reset();
    }
    
    public MonteCarloNode startWalk() {
        assert state == MonteCarloState.DISABLED;
        state = MonteCarloState.CUSTOM_WALK;
        return current;
    }
    
    public void endWalk(MonteCarloNode root) {
        assert state == MonteCarloState.CUSTOM_WALK;
        state = MonteCarloState.DISABLED;
        current = root;
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
        assert 0 < moveCount;
        switch (state) {
            case DISABLED:
            case CUSTOM_WALK:
                if (current.isLeaf()) {
                    current.initChilds(moveCount);
                }
                assert current.numChilds() == moveCount;
                return bestChild(player);
            case SELECT:
                if (current.isLeaf()) {
                    current.initChilds(moveCount);
                    state = MonteCarloState.PLAYOUT;
                }
                assert current.numChilds() == moveCount;
                return selectChild(player);
            case PLAYOUT:
                return rng.nextInt(moveCount);
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
                assert current.getChild(x).visitScore() == 0 || (0 <= winrate && winrate <= 1);
                scores += ((int) (winrate * 100)) + "%";
                if (x == best) {
                    scores += "}";
                }
            }
            scores = scores.replaceFirst(", ", "");
            System.out.println("simulation-strength: " + simulationStrength());
            System.out.println(scores);
        }
        assert 0 <= best && best < current.numChilds();
        return best;
    }
    
    public int simulationStrength() {
        return current.visitScore() / 2;
    }

    public void move(int move, int moveCount) {
        assert 0 <= move && move < moveCount: move + ", " + moveCount;
        switch (state) {
            case SELECT:
                if (current.isLeaf()) {
                    state = MonteCarloState.PLAYOUT;
                    break;
                }
                assert current.numChilds() == moveCount;
                current = current.getChild(move);
                nodeHistory.add(current);
                break;
            case DISABLED:
            case CUSTOM_WALK:
                if (current.isLeaf()) {
                    Arrays.fill(current.scores, 0);
//                    current = new MonteCarloNode();
                } else {
                    assert current.numChilds() == moveCount;
                    current = current.getChild(move);
                }
                break;
            case PLAYOUT:
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
            uctValue += EPSILON * rng.nextFloat();
            if (uctValue > bestValue) {
                bestValue = uctValue;
                best = i;
            }
        }
        assert 0 <= best && best < current.numChilds();
        return best;
    }

    private float calcUtc(float totalScore, float childTotal, float childScore) {
        childTotal += EPSILON;
        totalScore += 1;
        float exploitation = childScore / childTotal;
        float exploration = CONSTANT * (float) (Math.sqrt(Math.log(totalScore) / childTotal));
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
