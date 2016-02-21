package com.etherblood.cardsmasterserver.network.events;

import org.springframework.context.ApplicationEvent;

/**
 *
 * @author Philipp
 */
public class UserLogoutEvent extends ApplicationEvent {

    public UserLogoutEvent(Long source) {
        super(source);
    }

    public Long getUserId() {
        return (Long) getSource();
    }
}
