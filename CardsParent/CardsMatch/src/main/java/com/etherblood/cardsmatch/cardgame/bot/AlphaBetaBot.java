///*
// * To change this template, choose Tools | Templates
// * and open the template in the editor.
// */
//package com.etherblood.cardsmatch.cardgame.bot;
//
//import com.etherblood.cardsmatch.cardgame.MatchState;
//import com.etherblood.cardsmatch.cardgame.bot.commands.Command;
//import com.etherblood.cardsmatch.cardgame.bot.commands.CommandManager;
//import com.etherblood.cardsmatch.cardgame.components.battle.hero.HeroComponent;
//import com.etherblood.cardsmatch.cardgame.components.battle.stats.HealthComponent;
//import com.etherblood.cardsmatch.cardgame.components.cards.cardZone.BoardCardComponent;
//import com.etherblood.cardsmatch.cardgame.components.misc.OwnerComponent;
//import com.etherblood.cardsmatch.cardgame.components.player.ItsMyTurnComponent;
//import com.etherblood.cardsmatch.cardgame.components.player.NextTurnPlayerComponent;
//import com.etherblood.cardsmatch.cardgame.components.player.PlayerComponent;
//import com.etherblood.entitysystem.data.EntityId;
//import com.etherblood.entitysystem.filters.AbstractComponentFieldValueFilter;
//import com.etherblood.entitysystem.filters.EqualityOperator;
//import com.etherblood.entitysystem.filters.FilterQuery;
//import com.etherblood.entitysystem.version.VersionedEntityComponentMap;
//import java.util.HashMap;
//import java.util.List;
//
///**
// *
// * @author Philipp
// */
//public class AlphaBetaBot implements Bot {
//
//    private final HashMap<Integer, Integer> transpositionMap = new HashMap<>();
//    private final HashMap<Integer, Integer> transpositionVersionMap = new HashMap<>();
//    private int hash;
//    public boolean ttEnabled = true;
//    private final static int DEPTH = 2;
//    private final Evaluation eval;
//    private final CommandManager exec;
//    private final MatchState match;
//    private final VersionedEntityComponentMap data;
//    private Command bestCommand;
//
//    public AlphaBetaBot(Evaluation eval, MatchState match) {
//        this.eval = eval;
//        this.exec = match.getContext().getBean(CommandManager.class);
//        this.match = match;
//        data = match.data;
//    }
//    long evals = 0, nodes = 0, millis = 0;
//
//    @Override
//    public Command think() {
//        match.setLoggingEnabled(false);
//        transpositionMap.clear();
//        transpositionVersionMap.clear();
//        hash = 0;
//        millis -= System.currentTimeMillis();
//        int version = data.getVersion();
//        alphaBeta(DEPTH, -Integer.MAX_VALUE, Integer.MAX_VALUE);
//        if (data.getVersion() != version) {
//            throw new RuntimeException("state was not reset successfully, before=" + version + ", after=" + data.getVersion());
//        }
//        millis += System.currentTimeMillis();
////        System.err.println("TOTAL: nodes: " + nodes + " evals: " + evals + " millis: " + millis);
////        System.err.println("TT: hits: " + ttHits + " misses: " + ttMisses);
//        match.setLoggingEnabled(true);
//        return bestCommand;
//    }
//    private int ttHits, ttMisses;
//    private FilterQuery heroQuery = new FilterQuery()
//            .setBaseClass(HeroComponent.class)
//            .addComponentClassFilter(BoardCardComponent.class)
//            .addComponentClassFilter(HealthComponent.class);
//
//    private int alphaBeta(int depthRemain, int alpha, int beta) {
//        nodes++;
//        if (ttEnabled) {
//            Integer hashScore = transpositionMap.get(hash);
//            if (hashScore != null && data.getVersion() == transpositionVersionMap.get(hash)) {
//                ttHits++;
//                return hashScore;
//            }
//            ttMisses++;
//        }
//        if (depthRemain <= 0 || heroQuery.count(data) != 2) {
//            return evaluate();
//        }
//
//        EntityId currentPlayer = currentPlayerQuery.first(data);
//        int version = data.getVersion();
////        List<Command> commands = exec.generateCommands(match, depthRemain == DEPTH);
//        List<Command> commands = exec.generateCommands(depthRemain == DEPTH);
//        for (Command command : commands) {
//            if(exec.executeCommand(currentPlayer, command)) {
//                if (ttEnabled) {
//                    hash ^= command.hashCode();
//                }
//                EntityId nextPlayer = currentPlayerQuery.first(data);
//                int score;
//                if (currentPlayer == nextPlayer) {
//                    score = alphaBeta(depthRemain, alpha, beta);
//                } else {
//                    if (depthRemain == 2) {
//                        score = -evaluate();
//                        if (score <= alpha) {
//                            data.revertTo(version);
//                            if (ttEnabled) {
//                                hash ^= command.hashCode();
//                            }
//                            continue;
//                        }
//                    }
//                    score = -alphaBeta(depthRemain - 1, -beta, -alpha);
//                }
//                data.revertTo(version);
//                if (ttEnabled) {
//                    hash ^= command.hashCode();
//                }
//                if (data.getVersion() != version) {
//                    throw new RuntimeException("resetting doesnt work");
//                }
//                if (alpha < score) {
//                    if (beta <= score) {
//                        return beta;
//                    }
//                    alpha = score;
//                    if (depthRemain == DEPTH) {
//                        bestCommand = command;
//                    }
//                }
//            }
//        }
//        if (ttEnabled) {
//            transpositionMap.put(hash, alpha);
//            transpositionVersionMap.put(hash, version);
//        }
//        return alpha;
//    }
//    private final AbstractComponentFieldValueFilter<OwnerComponent> ownerFilter = OwnerComponent.createPlayerFilter(new EqualityOperator());
//    private final FilterQuery currentPlayerQuery = new FilterQuery().setBaseClass(ItsMyTurnComponent.class).addComponentClassFilter(PlayerComponent.class).addComponentClassFilter(NextTurnPlayerComponent.class);
//    private final FilterQuery playerHeroQuery = new FilterQuery().setBaseClass(HeroComponent.class).addComponentFilter(ownerFilter).addComponentClassFilter(HealthComponent.class);
//    
//    private int evaluate() {
//        evals++;
//        EntityId currentPlayer = currentPlayerQuery.first(match.data);
//        EntityId opponent = match.data.get(currentPlayer, NextTurnPlayerComponent.class).player;
//        int score = eval.evaluate(match.data, currentPlayer, opponent);
////        EntityId hero = playerHeroQuery.first(data);
////        if(hero != null) {
////            int health = data.get(hero, HealthComponent.class).health;
////            int attack = 0;
////            for (EntityId entityId : ownerAttackerQuery.list(data)) {
////                attack += data.get(entityId, AttackComponent.class).attack;
////            }
////            if(attack >= health) {
////                score += 500000;
////            }
////        }
//        return score;
//    }
//}
