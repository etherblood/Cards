package com.etherblood.cardsmasterserver.matches;

import com.etherblood.cardsmasterserver.matches.internal.MatchContextWrapper;
import org.springframework.context.ApplicationEvent;

/**
 *
 * @author Philipp
 */
public class AiUpdateRequest extends ApplicationEvent {

    public AiUpdateRequest(MatchContextWrapper source) {
        super(source);
    }

    public MatchContextWrapper getMatchWrapper() {
        return (MatchContextWrapper) getSource();
    }
}
