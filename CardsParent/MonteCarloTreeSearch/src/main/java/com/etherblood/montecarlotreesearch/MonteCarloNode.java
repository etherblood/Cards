package com.etherblood.montecarlotreesearch;

/**
 *
 * @author Philipp
 */
public class MonteCarloNode {

    private MonteCarloNode[] childs;
    public final int[] scores = new int[2];
//    public int score, visits;

    public int numChilds() {
        return childs.length;
    }
    
    public void initChilds(int size) {
        assert childs == null;
        childs = new MonteCarloNode[size];
    }

    public MonteCarloNode getChild(int x) {
        if (childs[x] == null) {
            childs[x] = new MonteCarloNode();
        }
        return childs[x];
    }

    public void setChild(int x, MonteCarloNode child) {
        childs[x] = child;
    }

    public int visitScore() {
        return scores[0] + scores[1];
    }

    public int playerScore(int player) {
        return scores[player];
    }
    
//    public int opponentScore() {
//        return visitScore() - playerScore();
//    }

    public float winrate(int player) {
        return (float) playerScore(player) / visitScore();
    }

    public boolean isLeaf() {
        return childs == null;
    }
    
//    public int childScoreSum() {
//        int sum = 0;
//        for (MonteCarloNode node : childs) {
//            if(node != null) {
//                sum += node.score;
//            }
//        }
//        return sum;
//    }
}
