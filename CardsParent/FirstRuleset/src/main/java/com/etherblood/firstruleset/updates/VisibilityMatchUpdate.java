package com.etherblood.firstruleset.updates;

/**
 *
 * @author Philipp
 */
public class VisibilityMatchUpdate<T> {
    private final int visibility;
    private final T update;

    public VisibilityMatchUpdate(int visibility, T update) {
        this.visibility = visibility;
        this.update = update;
    }

    public int getVisibility() {
        return visibility;
    }

    public T getUpdate() {
        return update;
    }
}
