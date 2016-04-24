package com.etherblood.eventsystem;

import java.util.*;

/**
 *
 * @author Philipp
 */
public final class GameEventQueueImpl implements GameEventQueue {

    public static int SUCCESSIVE_EVENTS_LIMIT = 10000;

    private final GameEventDispatcher dispatcher;
    private final ArrayList<ArrayDeque<GameEvent>> queues = new ArrayList<>();
    private int depth = 0;
    private int successiveEventsCount;

    private final HashSet<GameEvent> handledEvents = new HashSet<>();

    public GameEventQueueImpl(GameEventDispatcher dispatcher) {
        this.dispatcher = dispatcher;
    }

    public void handleEvents() {
        successiveEventsCount = 0;
        while (!rootQueue().isEmpty()) {
            handleEventsIteration();
        }
        handledEvents.clear();
    }

    private void handleEventsIteration() throws RuntimeException {
        if (currentQueue().isEmpty()) {
            depth--;
            currentQueue().removeFirst();//all response-events were handled, remove current event
            return;
        }
        GameEvent event = currentQueue().peekFirst();
        depth++;
        assert handledEvents.add(event);
        try {
            dispatcher.dispatch(event);//response-events will be put into currentQueue
        } catch (Exception ex) {
            System.out.println("eventQueue at time of exception:");
            for (int i = 0; i < depth; i++) {
                if(i != 0) {
                    System.out.println("childs:");
                }
                for (GameEvent gameEvent : queues.get(i)) {
                    System.out.println(gameEvent);
                }
            }
            throw ex;
        }
        if (++successiveEventsCount >= SUCCESSIVE_EVENTS_LIMIT) {
            throw new RuntimeException("Successive events limit of " + SUCCESSIVE_EVENTS_LIMIT + " was surpassed, your events might cause an infinite loop");
        }
    }

    @Override
    public void fireEvent(GameEvent event) {
        currentQueue().addLast(event);
    }
    
    public GameEvent getParent(GameEvent event) {
        for (int i = depth; i > 0; i--) {
            if(queue(i).peekFirst().equals(event)) {
                return queue(i - 1).peekFirst();
            }
        }
        return null;
    }

    private ArrayDeque<GameEvent> rootQueue() {
        return queue(0);
    }

    private ArrayDeque<GameEvent> currentQueue() {
        return queue(depth);
    }

    private ArrayDeque<GameEvent> queue(int depth) {
        while (queues.size() <= depth) {
            queues.add(new ArrayDeque<>());
        }
        return queues.get(depth);
    }

    public boolean isEmpty() {
        return rootQueue().isEmpty();
    }

    public void clear() {
        queues.clear();
        handledEvents.clear();
    }
}
