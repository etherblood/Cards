package com.etherblood.cardsjmeclient.match.animations.base;

/**
 *
 * @author Philipp
 */
public class AnimationComponent implements Animation {
    private final Animation animation;
    private float start;

    public AnimationComponent(Animation animation) {
        this.animation = animation;
    }

    public Animation getWrappedAnimation() {
        return animation;
    }

    public float getStart() {
        return start;
    }

    public void setStart(float start) {
        this.start = start;
    }
    
    public float getEnd() {
        return start + animation.durationSeconds();
    }

    @Override
    public float durationSeconds() {
        return animation.durationSeconds();
    }

    @Override
    public void update(float elapsedSeconds) {
        animation.update(elapsedSeconds);
    }

    @Override
    public void init() {
        animation.init();
    }

    @Override
    public void cleanup() {
        animation.cleanup();
    }
}
