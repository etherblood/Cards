package com.etherblood.firstruleset.updates;

import com.etherblood.eventsystem.GameEvent;
import java.util.List;

/**
 *
 * @author Philipp
 */
public interface UpdateBuilder<T, E extends GameEvent> {
    void fromEvent(E event, List<VisibilityMatchUpdate<T>> results);
}
