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
    private final GameEventDataStack dataStack = new GameEventDataStack();
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
            dataStack.down();
            currentQueue().removeFirst();//all response-events were handled, remove current event
            return;
        }
        GameEvent event = currentQueue().peekFirst();
        dataStack.up();
        assert handledEvents.add(event);
        try {
            dispatcher.dispatch(event);//response-events will be put into currentQueue
        } catch (Exception ex) {
            System.out.println("eventQueue at time of exception:");
            for (ArrayDeque<GameEvent> queue : queues) {
                for (GameEvent gameEvent : queue) {
                    System.out.println(gameEvent);
                }
                System.out.println("");
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

    private ArrayDeque<GameEvent> rootQueue() {
        return queue(0);
    }

    private ArrayDeque<GameEvent> currentQueue() {
        return queue(dataStack.depth());
    }

    private ArrayDeque<GameEvent> queue(int depth) {
        while (queues.size() <= depth) {
            queues.add(new ArrayDeque<GameEvent>());
        }
        return queues.get(depth);
    }

    public GameEventDataStack getDataStack() {
        return dataStack;
    }

    public boolean isEmpty() {
        return rootQueue().isEmpty();
    }

    public void clear() {
        dataStack.clear();
        queues.clear();
        handledEvents.clear();
    }
}
