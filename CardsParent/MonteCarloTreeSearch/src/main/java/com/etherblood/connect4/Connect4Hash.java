package com.etherblood.connect4;

/**
 *
 * @author Philipp
 */
public class Connect4Hash {
    private final int width;

    public Connect4Hash(int width, int height) {
        if(width < 4 || height < 4 || width * (height + 1) >= 64) {
            throw new IllegalArgumentException();
        }
        this.width = width;
    }

    public long hash(long white, long black) {
        long allTokens = white | black;
        allTokens |= allTokens << width;
        return allTokens ^ black;//TODO: bitshuffling
    }

    public int intHash(long white, long black) {
        return Long.hashCode(hash(white, black));
    }
}
