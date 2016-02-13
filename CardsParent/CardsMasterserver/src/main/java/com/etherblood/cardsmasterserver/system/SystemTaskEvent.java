package com.etherblood.cardsmasterserver.system;

import org.springframework.context.ApplicationEvent;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

/**
 *
 * @author Philipp
 */
public class SystemTaskEvent extends ApplicationEvent {

    private final Authentication issuedBy;
    
    public SystemTaskEvent(ApplicationEvent source) {
        super(source);
        issuedBy = SecurityContextHolder.getContext().getAuthentication();
    }

    public ApplicationEvent getEvent() {
        return (ApplicationEvent) getSource();
    }

    public Authentication getIssuedBy() {
        return issuedBy;
    }
}
