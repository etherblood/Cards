package com.etherblood.eventsystem;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


class MultiEvent implements GameEvent {
    public final List<GameEvent> events;

    public MultiEvent(List<GameEvent> events) {
        this.events = new ArrayList<>(events);
    }

    @Override
    public String toString() {
        return "MultiEvent{" + "events=" + Arrays.toString(events.toArray()) + '}';
    }
}
