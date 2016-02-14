package com.etherblood.cardsmasterserver.users;

import com.etherblood.cardsmasterserver.core.AbstractRepository;
import com.etherblood.cardsmasterserver.users.model.QUserAccount;
import com.etherblood.cardsmasterserver.users.model.UserAccount;
import org.springframework.stereotype.Repository;

/**
 *
 * @author Philipp
 */
@Repository
public class UserRepository extends AbstractRepository<UserAccount> {
    private static final QUserAccount userAccount = QUserAccount.userAccount;

    public UserAccount findByName(String username) {
        return from(userAccount).where(userAccount.username.equalsIgnoreCase(username)).singleResult(userAccount);
    }
    public UserAccount findById(long userId) {
        return from(userAccount).where(userAccount.id.eq(userId)).singleResult(userAccount);
    }
    
    public String getRoles(UserAccount user) {
        return "ROLE_USER";//TODO
    }
}
