package com.etherblood.cardsmasterserver.matches;

import com.etherblood.cardsmasterserver.matches.internal.MatchWrapper;
import org.springframework.context.ApplicationEvent;

/**
 *
 * @author Philipp
 */
public class AiUpdateRequest extends ApplicationEvent {

    public AiUpdateRequest(MatchWrapper source) {
        super(source);
    }

    public MatchWrapper getMatchWrapper() {
        return (MatchWrapper) getSource();
    }
}
