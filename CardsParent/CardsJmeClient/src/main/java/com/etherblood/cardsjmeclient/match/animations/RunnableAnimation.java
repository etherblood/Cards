package com.etherblood.cardsjmeclient.match.animations;

import com.etherblood.cardsjmeclient.match.animations.base.Animation;

/**
 *
 * @author Philipp
 */
public class RunnableAnimation implements Animation {
    private final Runnable runnable;

    public RunnableAnimation(Runnable runnable) {
        this.runnable = runnable;
    }
    @Override
    public float durationSeconds() {
        return 0;
    }

    @Override
    public void update(float totalElapsedSeconds) {
    }

    @Override
    public void init() {
        runnable.run();
    }

    @Override
    public void cleanup() {
    }

}
