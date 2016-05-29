package com.etherblood.cardsmasterserver.matches;

import com.etherblood.cardsmasterserver.users.model.UserAccount;
import com.etherblood.cardsmatchapi.PlayerDefinition;
import java.util.Objects;

/**
 *
 * @author Philipp
 */
public class MatchQueuePlayer {
    private PlayerDefinition def;
    private UserAccount user;
    
    public boolean isBot() {
        return user == null;
    }

    public PlayerDefinition getDef() {
        return def;
    }

    public void setDef(PlayerDefinition def) {
        this.def = def;
    }

    public UserAccount getUser() {
        return user;
    }

    public void setUser(UserAccount user) {
        this.user = user;
    }

    @Override
    public int hashCode() {
        return Long.hashCode(user.getId());
    }

    @Override
    public boolean equals(Object obj) {
        MatchQueuePlayer other = (MatchQueuePlayer) obj;
        return Objects.equals(this.user.getId(), other.user.getId());
    }
}
