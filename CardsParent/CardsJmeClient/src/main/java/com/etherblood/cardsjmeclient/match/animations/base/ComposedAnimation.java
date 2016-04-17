package com.etherblood.cardsjmeclient.match.animations.base;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Philipp
 */
public class ComposedAnimation implements Animation {
    private final List<AnimationComponent> waitingAnimations;
    private final List<AnimationComponent> activeAnimations = new ArrayList<>();
    private final float durationSeconds;

    public ComposedAnimation(List<AnimationComponent> animations) {
        this.waitingAnimations = animations;
        float duration = 0;
        for (AnimationComponent animation : animations) {
            duration = Math.max(duration, animation.getEnd());
        }
        durationSeconds = duration;
    }
    @Override
    public float durationSeconds() {
        return durationSeconds;
    }

    @Override
    public void update(float elapsedSeconds) {
        startAnimationsForElapsed(elapsedSeconds);
        endAnimationsForElapsed(elapsedSeconds);
        for (AnimationComponent animation : activeAnimations) {
            animation.update(elapsedSeconds - animation.getStart());
        }
    }

    private void startAnimationsForElapsed(float elapsedSeconds) {
        for (int i = waitingAnimations.size() - 1; i < waitingAnimations.size(); i--) {
            AnimationComponent animation = waitingAnimations.get(i);
            if(animation.getStart() <= elapsedSeconds) {
                waitingAnimations.remove(i);
                activeAnimations.add(animation);
                animation.init();
            }
        }
    }

    private void endAnimationsForElapsed(float elapsedSeconds) {
        for (int i = activeAnimations.size() - 1; i < activeAnimations.size(); i--) {
            AnimationComponent animation = activeAnimations.get(i);
            if(animation.getEnd() <= elapsedSeconds) {
                activeAnimations.remove(i);
                animation.cleanup();
            }
        }
    }

    @Override
    public void init() {
        startAnimationsForElapsed(0);
    }

    @Override
    public void cleanup() {
        endAnimationsForElapsed(durationSeconds());
        if(!waitingAnimations.isEmpty() || !activeAnimations.isEmpty()) {
            throw new IllegalStateException();
        }
    }
}
