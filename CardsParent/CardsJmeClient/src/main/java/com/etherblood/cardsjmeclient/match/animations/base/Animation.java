package com.etherblood.cardsjmeclient.match.animations.base;

/**
 *
 * @author Philipp
 */
public interface Animation {
    float durationSeconds();
    void update(float totalElapsedSeconds);
    void init();
    void cleanup();
}
