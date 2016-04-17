package com.etherblood.connect4;

import java.util.Arrays;

/**
 *
 * @author Philipp
 */
public class Connect4 {

    private final int width, height;
    private final long[] winMasks = new long[4];
    private final int[] winShifts = new int[4];
    private final long fullBoard;
    
    private final long[] playerTokens = new long[2];
    private int currentPlayer = 0;

    public Connect4() {
        this(7, 6);
    }

    public Connect4(int width, int height) {
        if(width < 4 || height < 4 || width * height >= 64) {
            throw new IllegalArgumentException();
        }
        this.width = width;
        this.height = height;
        
        long xAxis = singleBit(width) - 1;
        long yAxis = 0;
        for (int y = 0; y < height; y++) {
            yAxis |= singleBit(y * width);
        }
        fullBoard = xAxis * yAxis;
        
        winShifts[0] = 1;
        winShifts[1] = width - 1;
        winShifts[2] = width + 1;
        winShifts[3] = width;
        
        winMasks[0] = ~yAxis;
        winMasks[1] = ~(xAxis | (yAxis << (width - 1)));
        winMasks[2] = ~(xAxis | yAxis);
        winMasks[3] = ~xAxis;
        
        for (int i = 0; i < 4; i++) {
            winMasks[i] &= winMasks[i] << winShifts[i];
        }
    }
    
    public void copyStateFrom(Connect4 connect4) {
        assert width == connect4.width && height == connect4.height;
        playerTokens[0] = connect4.playerTokens[0];
        playerTokens[1] = connect4.playerTokens[1];
        currentPlayer = connect4.currentPlayer;
    }
    
    public void tokenMove(long token) {
        assert ((playerTokens[0] | playerTokens[1]) & token) == 0;
        playerTokens[currentPlayer] ^= token;
        currentPlayer ^= 1;
    }
    public void tokenUndo(long token) {
        currentPlayer ^= 1;
        playerTokens[currentPlayer] ^= token;
        assert ((playerTokens[0] | playerTokens[1]) & token) == 0;
    }

    public void reset() {
        Arrays.fill(playerTokens, 0);
        currentPlayer = 0;
    }
    
    public boolean hasGameEnded() {
        return isBoardFull() || opponentWon();
    }
    
    public long generateTokenMoves() {
        long allTokens = playerTokens[0] | playerTokens[1];
        return ~(allTokens | ~allTokens << width) & fullBoard;
    }
    
    public boolean opponentWon() {
        long tokens = playerTokens[opponent()];
        for (int i = 0; i < 4; i++) {
            long win = tokens;
            win &= win << (2 * winShifts[i]);
            win &= winMasks[i];
            win &= win << winShifts[i];
            if(win != 0) {
                return true;
            }
        }
        return false;
    }
    
    public boolean isBoardFull() {
        return (playerTokens[0] | playerTokens[1]) == fullBoard;
    }
    
    public void print() {
        for (int y = height - 1; y >= 0; y--) {
            for (int x = 0; x < width; x++) {
                long token = singleBit(x + y * width);
                if((playerTokens[0] & token) != 0) {
                    System.out.print("[X]");
                } else if((playerTokens[1] & token) != 0) {
                    System.out.print("[O]");
                } else {
                    System.out.print("[ ]");
                }
            }
            System.out.println();
        }
    }
    
    public void print(long tokens) {
        for (int y = height - 1; y >= 0; y--) {
            for (int x = 0; x < width; x++) {
                long token = singleBit(x + y * width);
                if((tokens & token) != 0) {
                    System.out.print("[X]");
                } else {
                    System.out.print("[ ]");
                }
            }
            System.out.println();
        }
    }
    
    private long singleBit(int index) {
        return 1L << index;
    }

    public int currentPlayer() {
        return currentPlayer;
    }
    
    public int opponent() {
        return currentPlayer ^ 1;
    }
    
    public int getWidth() {
        return width;
    }
}