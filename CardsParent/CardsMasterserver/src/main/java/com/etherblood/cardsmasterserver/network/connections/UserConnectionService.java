package com.etherblood.cardsmasterserver.network.connections;

import com.etherblood.cardsmasterserver.users.UserRoles;
import com.etherblood.cardsmasterserver.network.messages.MessageFromClientService;
import com.etherblood.cardsmasterserver.network.messages.MessageHandler;
import com.etherblood.cardsmasterserver.users.UserRepository;
import com.etherblood.cardsmasterserver.users.model.UserAccount;
import com.etherblood.cardsnetworkshared.master.commands.UserLogin;
import com.etherblood.cardsnetworkshared.master.commands.UserLogout;
import com.etherblood.cardsnetworkshared.match.misc.CardsMessage;
import com.jme3.network.ConnectionListener;
import com.jme3.network.HostedConnection;
import com.jme3.network.Message;
import com.jme3.network.MessageListener;
import com.jme3.network.Network;
import com.jme3.network.Server;
import java.io.IOException;
import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

/**
 *
 * @author Philipp
 */
@Service
public class UserConnectionService {
    private static final String AUTHENTICATION = "AUTHENTICATION";
    @Value("${host.port}")
    private int port;
    private Server server;
    @Autowired
    private MessageFromClientService messageService;
    @Autowired
    private UserRepository userRepo;
    
    @PostConstruct
    @PreAuthorize("denyAll")
    public void init() throws IOException {
        server = Network.createServer(port);
        server.addConnectionListener(new ConnectionListener() {
            @Override
            public void connectionAdded(Server server, HostedConnection connection) {
                setAnonymous(connection);
                System.out.println("new connection " + connection.getAddress());
            }
            @Override
            public void connectionRemoved(Server server, HostedConnection connection) {
                setAuthentication(connection, null);
                System.out.println("connection removed");
            }
        });
        server.addMessageListener(new MessageListener<HostedConnection>() {
            @Override
            public void messageReceived(HostedConnection connection, Message message) {
                attachAuthentication(connection);
                messageService.dispatchMessage(((CardsMessage)message).getData());
                detachAuthentication();
            }
        }, CardsMessage.class);
        server.start();
    }
    
    @PreDestroy
    @PreAuthorize("denyAll")
    public void cleanup() {
        server.close();
    }
    
    @MessageHandler
    @PreAuthorize("hasRole('ROLE_ANONYMOUS')")
    public void login(UserLogin userLogin) {
        UserAccount user = userRepo.findByName(userLogin.getUsername());
        if(!user.getPlaintextPassword().equals(userLogin.getPlaintextPassword())) {
            throw new RuntimeException("invalid password");
        }
        HostedConnection connection = getCurrentConnection();
        setAuthentication(connection, new DefaultAuthentication(connection, user.getId(), userRepo.getRoles(user)));
        System.out.println(connection.getAddress() + " logged in as " + user.getUsername());
    }
    
    @MessageHandler
    @PreAuthorize("hasRole('ROLE_USER')")
    public void logout(UserLogout userLogout) {
        System.out.println(getCurrentUser().getUsername() + " logged out.");
        setAnonymous(getCurrentConnection());
    }
    
    @PreAuthorize("hasAnyRole('ROLE_USER', 'ROLE_SYSTEM')")
    public void sendMessages(long userId, Message... messages) {
        for (Message message : messages) {
            sendMessage(userId, message);
        }
    }
    
    @PreAuthorize("hasAnyRole('ROLE_USER', 'ROLE_SYSTEM')")
    public void sendMessage(long userId, Message message) {
        for (HostedConnection connection : server.getConnections()) {
            Long id = getAuthentication(connection).getCredentials();
            if(id != null && id.longValue() == userId) {
                connection.send(message);
                return;
            }
        }
        throw new RuntimeException("can't send message, no connection with userId " + userId + " found.");
    }
    
    @PreAuthorize("hasAnyRole('ROLE_USER')")
    public void returnMessage(Message message) {
        getCurrentConnection().send(message);
    }
    
    @PreAuthorize("hasRole('ROLE_USER')")
    public UserAccount getCurrentUser() {
        return userRepo.findById(getAuthentication(getCurrentConnection()).getCredentials());
    }
    @PreAuthorize("hasRole('ROLE_USER')")
    public Long getCurrentUserId() {
        return getAuthentication(getCurrentConnection()).getCredentials();
    }
    
    private void attachAuthentication(HostedConnection connection) {
        SecurityContextHolder.getContext().setAuthentication(getAuthentication(connection));
    }
    private void detachAuthentication() {
        SecurityContextHolder.clearContext();
    }
    
    private void setAnonymous(HostedConnection connection) {
        setAuthentication(connection, new DefaultAuthentication(connection, null, UserRoles.ANONYMOUS));
    }
    
    private HostedConnection getCurrentConnection() {
        return ((DefaultAuthentication<HostedConnection>)SecurityContextHolder.getContext().getAuthentication()).getPrincipal();
    }
    
    private void setAuthentication(HostedConnection connection, Authentication authentication) {
        connection.setAttribute(AUTHENTICATION, authentication);
    }
    
    private DefaultAuthentication<HostedConnection> getAuthentication(HostedConnection connection) {
        return connection.getAttribute(AUTHENTICATION);
    }
}