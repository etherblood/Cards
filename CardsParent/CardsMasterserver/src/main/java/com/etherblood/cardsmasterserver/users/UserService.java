package com.etherblood.cardsmasterserver.users;

import com.etherblood.cardsmasterserver.system.SystemTaskEvent;
import com.etherblood.cardsmasterserver.network.messages.MessageHandler;
import com.etherblood.cardsmasterserver.users.events.PreUserDeleteEvent;
import com.etherblood.cardsmasterserver.users.events.UserRegisteredEvent;
import com.etherblood.cardsmasterserver.users.model.UserAccount;
import com.etherblood.cardsnetworkshared.master.commands.UserRegistration;
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
    @Autowired
    private UserRepository userRepo;
    @Autowired
    private ApplicationEventPublisher eventPublisher;
    
    @Transactional
    @PreAuthorize("hasRole('ROLE_SYSTEM')")
    public UserAccount getUser(long userId) {
        return userRepo.findById(userId);
    }
    
    @Transactional
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public UserAccount getUser(String username) {
        return userRepo.findByName(username);
    }
    
    @MessageHandler
    @Transactional
    @PreAuthorize("permitAll")
    public void registerUser(UserRegistration userRegistration) {
        UserAccount user = new UserAccount();
        user.setUsername(userRegistration.getUsername());
        user.setPlaintextPassword(userRegistration.getPlaintextPassword());
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
}