package com.etherblood.montecarlotreesearch;

import java.util.Arrays;
import java.util.Random;

/**
 *
 * @author Philipp
 */
public class MonteCarloAgent<T> {
    private final static float epsilon = 1e-6f;
    private final static float constant = (float) Math.sqrt(2);
    
    private final Random rng = new Random();
    private final GameSimulation<T> simulation;
    private int numberPlayouts = 1;
    private boolean verbose = false;
    private final int[] playoutsResult = new int[3];

    public MonteCarloAgent(GameSimulation<T> simulation) {
        this.simulation = simulation;
    }
    
    public T calcBestMove(MonteCarloNode root, long timeoutMillis) {
        timeoutMillis += System.currentTimeMillis();
        while(System.currentTimeMillis() < timeoutMillis) {
            search(root);
        }
        int best = 0;
        for (int i = 1; i < root.numChilds(); i++) {
            if(root.getChild(i).visitScore() > root.getChild(best).visitScore()) {
                best = i;
            }
        }
        
        
        if (verbose) {
            int currentPlayer = simulation.currentPlayer();
            String scores = "";
            for (int x = 0; x < root.numChilds(); x++) {
                scores += ", ";
                if (x == best) {
                    scores += "{";
                }
                float winrate = root.getChild(x).winrate(currentPlayer);
                assert 0 <= winrate && winrate <= 1;
                scores += ((int) (winrate * 100)) + "%";
                if (x == best) {
                    scores += "}";
                }
            }
            scores = scores.replaceFirst(", ", "");
            System.out.println("simulation-strength: " + root.visitScore() / 2);
            System.out.println(scores);
        }
        
        MoveIterator<T> moves = simulation.generateMoves();
        moves.discardMoves(best);
        return moves.popMove();
    }
    
    private void search(MonteCarloNode node) {
        if(node.isLeaf()) {
            int victoryState = simulation.getVictoryState();
            if(victoryState != GameSimulation.ONGOING) {
                Arrays.fill(playoutsResult, 0);
                playoutsResult[victoryState] += numberPlayouts;
                node.scores[0] += 2 * playoutsResult[0] + playoutsResult[2];
                node.scores[1] += 2 * playoutsResult[1] + playoutsResult[2];
                return;
            }
            MoveIterator<T> moves = simulation.generateMoves();
            int numMoves = moves.size();
            node.initChilds(numMoves);
            int randomMove = rng.nextInt(numMoves);
            moves.discardMoves(randomMove);
            T move = moves.popMove();
            simulation.makeMove(move);
            randomPlayouts();
            MonteCarloNode child = node.getChild(randomMove);
            simulation.undo(move);
            child.scores[0] += 2 * playoutsResult[0] + playoutsResult[2];
            child.scores[1] += 2 * playoutsResult[1] + playoutsResult[2];
            node.scores[0] += 2 * playoutsResult[0] + playoutsResult[2];
            node.scores[1] += 2 * playoutsResult[1] + playoutsResult[2];
        } else {
            int selectedMove = selectChild(node, simulation.currentPlayer());
            MoveIterator<T> moves = simulation.generateMoves();
            moves.discardMoves(selectedMove);
            T move = moves.popMove();
            MonteCarloNode child = node.getChild(selectedMove);
            simulation.makeMove(move);
            search(child);
            simulation.undo(move);
            node.scores[0] += 2 * playoutsResult[0] + playoutsResult[2];
            node.scores[1] += 2 * playoutsResult[1] + playoutsResult[2];
        }
    }
    
    private void randomPlayouts() {
        Arrays.fill(playoutsResult, 0);
        for (int i = 0; i < numberPlayouts; i++) {
            playoutsResult[randomPlayout()]++;
        }
    }
    
    public int randomPlayout() {
        int victoryState = simulation.getVictoryState();
        if(victoryState != GameSimulation.ONGOING) {
            return victoryState;
        }
        MoveIterator<T> moves = simulation.generateMoves();
        int numMoves = moves.size();
        int randomMove = rng.nextInt(numMoves);
        moves.discardMoves(randomMove);
        T move = moves.popMove();
        simulation.makeMove(move);
        int result = randomPlayout();
        simulation.undo(move);
        return result;
    }
    
    private int selectChild(MonteCarloNode node, int player) {
        int best = -1;
        double bestValue = Double.NEGATIVE_INFINITY;
        for (int i = 0; i < node.numChilds(); i++) {
            MonteCarloNode child = node.getChild(i);
            float uctValue = calcUtc(node.visitScore(), child.visitScore(), child.playerScore(player));
            uctValue += epsilon * rng.nextFloat();
            if (uctValue > bestValue) {
                bestValue = uctValue;
                best = i;
            }
        }
        assert 0 <= best && best < node.numChilds();
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

    public void setNumberPlayouts(int numberPlayouts) {
        this.numberPlayouts = numberPlayouts;
    }

    public void setVerbose(boolean verbose) {
        this.verbose = verbose;
    }
}
