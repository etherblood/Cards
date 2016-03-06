package com.etherblood.cardsmasterserver.users;

import com.etherblood.cardsmasterserver.system.SystemTaskEvent;
import com.etherblood.cardsmasterserver.network.messages.MessageHandler;
import com.etherblood.cardsmasterserver.users.events.PreUserDeleteEvent;
import com.etherblood.cardsmasterserver.users.events.UserRegisteredEvent;
import com.etherblood.cardsmasterserver.users.model.Password;
import com.etherblood.cardsmasterserver.users.model.UserAccount;
import com.etherblood.cardsnetworkshared.master.commands.UserRegistration;
import java.security.SecureRandom;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.bind.DatatypeConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author Philipp
 */
@Service
public class UserService {

    private final PasswordHashing passwordHashing = new PasswordHashing();
    @Autowired
    private UserRepository userRepo;
    @Autowired
    private ApplicationEventPublisher eventPublisher;

    @Transactional
    @PreAuthorize("hasAnyRole('ROLE_SYSTEM', 'ROLE_USER')")
    public UserAccount getUser(long userId) {
        return userRepo.findById(userId);
    }

    @Transactional
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public UserAccount getUser(String username) {
        return userRepo.findByName(username);
    }

    @Transactional
    @PreAuthorize("permitAll")
    public UserAccount authenticateUser(String username, String plaintextPassword) {
        UserAccount user = getUser(username);
        Password password = user.getPassword();
        String passwordHash = calcPasswordHash(plaintextPassword, password.getSalt());
        if (!password.getHash().equals(passwordHash)) {
            try {
                Thread.sleep(200);
            } catch (InterruptedException ex) {
                ex.printStackTrace(System.err);
            }
            throw new RuntimeException("invalid password");
        }
        return user;
    }

    @MessageHandler
    @Transactional
    @PreAuthorize("permitAll")
    public void registerUser(UserRegistration userRegistration) {
        UserAccount oldUser = getUser(userRegistration.getUsername());
        if (oldUser != null) {
            throw new IllegalStateException("can't create user with name: " + userRegistration.getUsername() + " because user " + oldUser.getUsername() + " already exists.");
        }
        UserAccount user = new UserAccount();
        user.setUsername(userRegistration.getUsername());
        user.setPassword(generatePassword(userRegistration.getPlaintextPassword()));

        userRepo.persist(user);
        eventPublisher.publishEvent(new SystemTaskEvent(new UserRegisteredEvent(user)));
    }

    @Transactional
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public void deleteUser(String username) {
        UserAccount user = userRepo.findByName(username);
        eventPublisher.publishEvent(new PreUserDeleteEvent(user));
        userRepo.remove(user);
    }

    private Password generatePassword(String plaintextPassword) {
        SecureRandom random = new SecureRandom();
        byte[] bytes = new byte[32];
        random.nextBytes(bytes);
        String salt = DatatypeConverter.printHexBinary(bytes);

        Password password = new Password();
        password.setSalt(salt);
        password.setHash(calcPasswordHash(plaintextPassword, salt));
        return password;
    }

    private String calcPasswordHash(String plaintextPassword, String salt) {
        try {
            return passwordHashing.hashPassword(plaintextPassword, salt);
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
    }
}