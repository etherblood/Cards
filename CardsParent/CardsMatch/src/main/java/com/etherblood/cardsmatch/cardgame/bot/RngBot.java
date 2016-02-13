//package com.etherblood.cardsmatch.cardgame.bot;
//
//import com.etherblood.cardsmatch.cardgame.MatchState;
//import display.CommandExecutor;
//import display.CommandGenerator;
//import display.FakeCommand;
//import com.etherblood.entitysystem.version.VersionedEntityComponentMap;
//import java.util.List;
//import java.util.Random;
//
///**
// *
// * @author Philipp
// */
//public class RngBot implements Bot {
//    private final Random rng = new Random();
//    private final CommandGenerator gen;
//    private final MatchState match;
//
//    public RngBot(CommandGenerator gen, MatchState match) {
//        this.gen = gen;
//        this.match = match;
//    }
//    long evals = 0, nodes = 0, millis = 0;
//
//    @Override
//    public FakeCommand think() {
//        List<FakeCommand> commands = gen.generateCommands(match, true);
//        return commands.get(rng.nextInt(commands.size()));
//    }
//}
