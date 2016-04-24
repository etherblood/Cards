package com.etherblood.firstruleset.main;

import com.etherblood.cardscontext.MatchContext;
import com.etherblood.cardsmatch.cardgame.bot.Bot;
import com.etherblood.cardsmatch.cardgame.bot.commands.Command;
import com.etherblood.cardsmatchapi.BotProxy;
import com.etherblood.cardsmatchapi.PlayerDefinition;

/**
 *
 * @author Philipp
 */
public class BotProxyImpl extends AbstractPlayerProxy implements BotProxy {
    private Bot bot;

    public BotProxyImpl(MatchContext context, PlayerDefinition definition) {
        super(context, definition);
    }
    
    @Override
    public void doAction() {
        Command command = bot.think();
        apply(command.effect, command.targets);
    }

    @Override
    public void clearCache() {
        bot.clearCache();
    }

    public Bot getBot() {
        return bot;
    }

    public void setBot(Bot bot) {
        this.bot = bot;
    }

}
