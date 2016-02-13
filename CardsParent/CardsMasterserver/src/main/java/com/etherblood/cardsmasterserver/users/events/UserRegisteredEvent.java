package com.etherblood.cardsmasterserver.users.events;

import com.etherblood.cardsmasterserver.users.model.UserAccount;
import org.springframework.context.ApplicationEvent;

/**
 *
 * @author Philipp
 */
public class UserRegisteredEvent extends ApplicationEvent {

    public UserRegisteredEvent(UserAccount source) {
        super(source);
    }

    public UserAccount getUser() {
        return (UserAccount) getSource();
    }
}
