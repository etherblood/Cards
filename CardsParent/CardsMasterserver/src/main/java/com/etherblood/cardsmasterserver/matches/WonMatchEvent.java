package com.etherblood.cardsmasterserver.matches;

import com.etherblood.cardsmasterserver.matches.internal.players.HumanPlayer;
import org.springframework.context.ApplicationEvent;

/**
 *
 * @author Philipp
 */
public class WonMatchEvent extends ApplicationEvent {

    public WonMatchEvent(HumanPlayer source) {
        super(source);
    }

    public HumanPlayer getPlayer() {
        return (HumanPlayer) getSource();
    }
}
