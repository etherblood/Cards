package com.etherblood.cardsjmeclient.match.animations.base;

import java.util.LinkedList;
import java.util.List;

/**
 *
 * @author Philipp
 */
public class AnimationManager {
    private final LinkedList<Animation> animationQueue = new LinkedList<>();
    private AnimationComponent currentAnimation = null;
    private boolean instantAnimations = false;
    private boolean mergeAnimations = false;
    private float elapsedTime;
    private float animationSpeed = 1;
    
    public void update(float frameSeconds) {
        elapsedTime += frameSeconds * animationSpeed;
        while (currentAnimation != null) {
            float end = currentAnimation.getEnd();
            if(instantAnimations || end <= elapsedTime) {
                currentAnimation.cleanup();
                currentAnimation = nextAnimation();
                if (currentAnimation != null) {
                    startCurrentAnimation(end);
                }
            } else {
                currentAnimation.update(elapsedTime - currentAnimation.getStart());
            }
        }
    }
    
    public void enqueueAnimations(List<Animation> animations) {
        for (Animation animation : animations) {
            animationQueue.offer(animation);
        }
        if(currentAnimation == null) {
            currentAnimation = nextAnimation();
            startCurrentAnimation(elapsedTime);
        }
    }

    private void startCurrentAnimation(float start) {
        currentAnimation.setStart(start);
        currentAnimation.init();
    }

    private AnimationComponent nextAnimation() {
        if(mergeAnimations) {
        //TODO try merging available animations
        }
        return new AnimationComponent(animationQueue.poll());
    }

    public boolean isInstantAnimations() {
        return instantAnimations;
    }

    public void setInstantAnimations(boolean instantAnimations) {
        this.instantAnimations = instantAnimations;
    }

    public boolean isMergeAnimations() {
        return mergeAnimations;
    }

    public void setMergeAnimations(boolean mergeAnimations) {
        this.mergeAnimations = mergeAnimations;
    }

    public float getAnimationSpeed() {
        return animationSpeed;
    }

    public void setAnimationSpeed(float animationSpeed) {
        this.animationSpeed = animationSpeed;
    }
}
