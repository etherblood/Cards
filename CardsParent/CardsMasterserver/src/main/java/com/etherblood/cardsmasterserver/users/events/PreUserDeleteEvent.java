package com.etherblood.cardsmasterserver.users.events;

import com.etherblood.cardsmasterserver.users.model.UserAccount;
import org.springframework.context.ApplicationEvent;

/**
 *
 * @author Philipp
 */
public class PreUserDeleteEvent extends ApplicationEvent {

    public PreUserDeleteEvent(UserAccount source) {
        super(source);
    }

    public UserAccount getUser() {
        return (UserAccount) getSource();
    }
}
