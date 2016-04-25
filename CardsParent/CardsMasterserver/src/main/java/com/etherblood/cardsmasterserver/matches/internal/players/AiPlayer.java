package com.etherblood.cardsmasterserver.matches.internal.players;

import com.etherblood.cardsmasterserver.matches.internal.MatchContextWrapper;
import com.etherblood.cardsmatchapi.BotProxy;

/**
 *
 * @author Philipp
 */
public class AiPlayer extends AbstractPlayer {
    private final BotProxy proxy;

    public AiPlayer(BotProxy proxy) {
        this.proxy = proxy;
    }
    
    @Override
    public void clearCache() {
        proxy.clearCache();
    }

    public void compute() {
//        MatchContextWrapper matchWrapper = getMatch();
//        if (!matchWrapper.hasMatchEnded()) {
            proxy.doAction();
//        }
    }

    @Override
    public BotProxy getProxy() {
        return proxy;
    }
}
