package com.etherblood.cardsmatch.cardgame.bot;

import com.etherblood.cardsmatch.cardgame.MatchState;
import com.etherblood.cardsmatch.cardgame.bot.commands.Command;
import com.etherblood.cardsmatch.cardgame.bot.commands.EndTurnCommandFactory;
import com.etherblood.cardsmatch.cardgame.bot.commands.SummonCommandFactory;
import java.util.ArrayList;


public class SummonBot implements Bot {
    private final EndTurnCommandFactory endTurnFactory;
    private final SummonCommandFactory summonFactory;

    public SummonBot(MatchState matchState) {
        this.endTurnFactory = matchState.getContext().getBean(EndTurnCommandFactory.class);
        this.summonFactory = matchState.getContext().getBean(SummonCommandFactory.class);
    }

    @Override
    public Command think() {
        ArrayList<Command> list = new ArrayList<>();
        summonFactory.generateSummonCommands(list);
        if(list.isEmpty()) {
            return endTurnFactory.generateEndTurnCommand();
        }
        return list.get(0);
    }

}
