package com.etherblood.cardsmatch.cardgame.bot.monteCarlo;

import com.etherblood.cardsmatch.cardgame.ValidEffectTargetsSelector;
import com.etherblood.cardsmatch.cardgame.bot.Bot;
import com.etherblood.cardsmatch.cardgame.bot.commands.Command;
import com.etherblood.cardsmatch.cardgame.client.SystemsEventHandler;
import com.etherblood.cardsmatch.cardgame.client.SystemsEventHandlerDispatcher;
import com.etherblood.cardsmatch.cardgame.components.misc.MatchEndedComponent;
import com.etherblood.cardsmatch.cardgame.components.player.ItsMyTurnComponent;
import com.etherblood.cardsmatch.cardgame.components.player.WinnerComponent;
import com.etherblood.cardsmatch.cardgame.events.effects.TargetedTriggerEffectEvent;
import com.etherblood.cardsmatch.cardgame.events.effects.systems.triggers.TargetedTriggerEffectSystem;
import com.etherblood.cardsmatch.cardgame.rng.RngFactoryImpl;
import com.etherblood.cardsmatch.cardgame.rng.RngListener;
import com.etherblood.entitysystem.data.EntityComponentMap;
import com.etherblood.entitysystem.data.EntityComponentMapReadonly;
import com.etherblood.entitysystem.data.EntityId;
import com.etherblood.entitysystem.data.IncrementalEntityIdFactory;
import com.etherblood.eventsystem.GameEvent;
import com.etherblood.eventsystem.GameEventDataStack;
import com.etherblood.eventsystem.GameEventHandler;
import com.etherblood.eventsystem.GameEventQueueImpl;
import com.etherblood.match.MatchContext;
import com.etherblood.montecarlotreesearch.MonteCarloControls;
import com.etherblood.montecarlotreesearch.MonteCarloNode;
import com.etherblood.montecarlotreesearch.MonteCarloState;
import java.util.List;
import java.util.Set;

/**
 *
 * @author Philipp
 */
public class MonteCarloController implements Bot {

    long millis = 2000;
    private final EntityId player1Entity;
    private final MonteCarloControls controls = new MonteCarloControls();
    private final MatchContext state;
    private final MatchContext simulationState;
    private final CommandGenerator generator;
    private final MoveSelector simulationMoveSelector = new MoveSelector() {
        @Override
        public int selectMove(int count) {
            assert controls.state() != MonteCarloState.DISABLED && controls.state() != MonteCarloState.CUSTOM_WALK;
            int move = controls.selectMove(count, currentPlayer(simulationState.getBean(EntityComponentMapReadonly.class)));
            controls.move(move, count);
            return move;
        }
    };
    private final MoveSelector orginalMoveSelector = new MoveSelector() {
        @Override
        public int selectMove(int count) {
            assert controls.state() == MonteCarloState.CUSTOM_WALK;
            int move = controls.selectMove(count, currentPlayer(state.getBean(EntityComponentMapReadonly.class)));
            controls.move(move, count);
            return move;
        }
    };

    public MonteCarloController(MatchContext state, MatchContext simulationState, CommandGenerator commandGenerator, EntityId player1) {
        this.state = state;
        this.simulationState = simulationState;
        this.generator = commandGenerator;
        this.player1Entity = player1;
        RngListener rngListener = new RngListener() {
            @Override
            public void onRng(int result, int max) {
                controls.move(result, max);
            }
        };
        state.getBean(RngFactoryImpl.class).addListener(rngListener);
        simulationState.getBean(RngFactoryImpl.class).addListener(rngListener);

        SystemsEventHandlerDispatcher dispatcher = state.getBean(SystemsEventHandlerDispatcher.class);
        List<SystemsEventHandler> handlers = dispatcher.getHandlers();
        handlers.add(new SystemsEventHandler() {
            private MoveConsumer moveConsumer = new MoveConsumer() {
                @Override
                public void applyMove(int move, int count) {
                    assert controls.state() == MonteCarloState.DISABLED;
                    controls.move(move, count);
                }
            };

            @Override
            public <T extends GameEvent> void onEvent(Class<GameEventHandler<T>> systemClass, T gameEvent) {
                if (TargetedTriggerEffectSystem.class.equals(systemClass)) {
                    TargetedTriggerEffectEvent event = (TargetedTriggerEffectEvent) gameEvent;
                    try {
                        generator.applyCommand(MonteCarloController.this.state.getBean(EntityComponentMapReadonly.class), MonteCarloController.this.state.getBean(ValidEffectTargetsSelector.class), event.effect, event.targets, moveConsumer);
                    } catch (IllegalArgumentException e) {
                        System.out.println("skipping bot state update because of exception:");
                        e.printStackTrace(System.out);
                    }
                }
            }
        });
    }

    @Override
    public void clearCache() {
        controls.clear();
    }

    private void resetSimulationState() {
        assert simulationState.getBean(GameEventQueueImpl.class).isEmpty();
        assert state.getBean(GameEventQueueImpl.class).isEmpty();

        assert simulationState.getBean(GameEventDataStack.class).isEmpty();
        assert state.getBean(GameEventDataStack.class).isEmpty();

        EntityComponentMap destData = simulationState.getBean(EntityComponentMap.class);
        EntityComponentMap sourceData = state.getBean(EntityComponentMap.class);
        destData.copyFrom(sourceData);

        IncrementalEntityIdFactory sourceIds = state.getBean(IncrementalEntityIdFactory.class);
        IncrementalEntityIdFactory destIds = simulationState.getBean(IncrementalEntityIdFactory.class);
        destIds.setNextId(sourceIds.getNextId());

    }

    @Override
    public Command think() {
        long endMillis = System.currentTimeMillis() + millis;
        while (System.currentTimeMillis() < endMillis) {
            iteration();
        }
        MonteCarloNode root = controls.startWalk();
        Command command = generator.generate(state.getBean(EntityComponentMapReadonly.class), state.getBean(ValidEffectTargetsSelector.class), orginalMoveSelector);
        controls.endWalk(root);
        return command;
    }

    private void iteration() {
        resetSimulationState();
        controls.startIteration();
        playWhileInState(MonteCarloState.SELECT);
        playWhileInState(MonteCarloState.PLAYOUT);
        controls.endIteration();
    }

    private void playWhileInState(MonteCarloState loopState) {
        EntityComponentMapReadonly simulationData = simulationState.getBean(EntityComponentMapReadonly.class);
        ValidEffectTargetsSelector targetSelector = simulationState.getBean(ValidEffectTargetsSelector.class);
        while (controls.state() == loopState) {
            Command command = generator.generate(simulationData, targetSelector, simulationMoveSelector);
            applyCommand(simulationState, command);

            int victor = getVictoryState(simulationData);
            if (victor != MonteCarloControls.ONGOING) {
                controls.declareVictor(victor);
            }
        }
    }

    private void applyCommand(MatchContext context, Command command) {
        GameEventQueueImpl events = context.getBean(GameEventQueueImpl.class);
        events.fireEvent(new TargetedTriggerEffectEvent(command.effect, command.targets));
        events.handleEvents();
    }

    private int getVictoryState(EntityComponentMapReadonly data) {
        if (data.entities(MatchEndedComponent.class).isEmpty()) {
            return MonteCarloControls.ONGOING;
        } else {
            Set<EntityId> winners = data.entities(WinnerComponent.class);
            if (winners.isEmpty()) {
                return MonteCarloControls.DRAW;
            } else {
                return player1Entity.equals(winners.iterator().next()) ? 0 : 1;
            }
        }
    }

    private int currentPlayer(EntityComponentMapReadonly simulationData) {
        return player1Entity.equals(simulationData.entities(ItsMyTurnComponent.class).iterator().next()) ? 0 : 1;
    }
}
