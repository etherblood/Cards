package com.etherblood.cardsmasterserver.network.connections;

import com.etherblood.cardsmasterserver.logging.LoggerService;
import com.etherblood.cardsnetworkshared.ExtendedDefaultServer;
import com.etherblood.cardsmasterserver.network.events.UserLogoutEvent;
import com.etherblood.cardsmasterserver.users.UserRoles;
import com.etherblood.cardsmasterserver.network.messages.MessageFromClientService;
import com.etherblood.cardsmasterserver.network.messages.MessageHandler;
import com.etherblood.cardsmasterserver.system.SystemTaskEvent;
import com.etherblood.cardsmasterserver.users.UserService;
import com.etherblood.cardsmasterserver.users.model.UserAccount;
import com.etherblood.cardsnetworkshared.master.commands.UserLogin;
import com.etherblood.cardsnetworkshared.master.commands.UserLogout;
import com.etherblood.cardsnetworkshared.DefaultMessage;
import com.etherblood.cardsnetworkshared.EncryptedMessage;
import com.etherblood.cardsnetworkshared.master.updates.LoginSuccess;
import com.etherblood.logging.LogLevel;
import com.jme3.network.ConnectionListener;
import com.jme3.network.HostedConnection;
import com.jme3.network.Message;
import com.jme3.network.MessageListener;
import com.jme3.network.Server;
import java.io.IOException;
import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationEventPublisher;
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
    private UserService userService;
    @Autowired
    private ApplicationEventPublisher eventPublisher;
    @Autowired
    private LoggerService logger;

    @PostConstruct
    @PreAuthorize("denyAll")
    public void init() throws IOException {
        server = new ExtendedDefaultServer(port);//Network.createServer(port);
        server.addConnectionListener(new ConnectionListener() {
            @Override
            public void connectionAdded(Server server, HostedConnection connection) {
                setAnonymous(connection);
                System.out.println("new connection " + connection.getAddress());
                logger.getLogger(getClass()).log(LogLevel.INFO, "new connection {}", connection.getAddress());
            }

            @Override
            public void connectionRemoved(Server server, HostedConnection connection) {
                setAuthentication(connection, null);
                System.out.println("connection removed");
                logger.getLogger(getClass()).log(LogLevel.INFO, "connection removed");
            }
        });
        final MessageListener<HostedConnection> defaultMessageListener = new MessageListener<HostedConnection>() {
            @Override
            public void messageReceived(HostedConnection connection, Message message) {
                try {
                    attachAuthentication(connection);
                    Object data = ((DefaultMessage) message).getData();
                    messageService.dispatchMessage(data);
                } finally {
                    detachAuthentication();
                }
            }
        };
        final MessageListener<HostedConnection> encryptedMessageListener = new MessageListener<HostedConnection>() {
            @Override
            public void messageReceived(HostedConnection connection, Message message) {
                defaultMessageListener.messageReceived(connection, ((EncryptedMessage)message).getMessage());
            }
        };
        server.addMessageListener(defaultMessageListener, DefaultMessage.class);
        server.addMessageListener(encryptedMessageListener, EncryptedMessage.class);
        server.start();
    }

    @PreDestroy
    @PreAuthorize("denyAll")
    public void cleanup() {
        server.close();
    }

    @MessageHandler
    @PreAuthorize("hasRole('ROLE_ANONYMOUS')")
    public LoginSuccess login(UserLogin userLogin) {
        UserAccount user = userService.authenticateUser(userLogin.getUsername(), userLogin.getPlaintextPassword());
        HostedConnection previousConnection = findUserConnection(user.getId());
        if (previousConnection != null) {
            setAnonymous(previousConnection);
            System.out.println("user " + user.getUsername() + " was kicked because he logged in a second time.");
                logger.getLogger(getClass()).log(LogLevel.INFO, "user {} was kicked because he logged in a second time.", user.getUsername());
        }
        HostedConnection connection = getCurrentConnection();
        setAuthentication(connection, new DefaultAuthentication(connection, user.getId(), user.getRoles()));
        System.out.println(connection.getAddress() + " logged in as " + user.getUsername());
        logger.getLogger(getClass()).log(LogLevel.INFO, "{} logged in as {}", connection.getAddress(), user.getUsername());
        return new LoginSuccess(user.getUsername());
    }

    @MessageHandler
    @PreAuthorize("hasRole('ROLE_USER')")
    public void logout(UserLogout userLogout) {
        System.out.println(getCurrentUser().getUsername() + " logged out.");
        logger.getLogger(getClass()).log(LogLevel.INFO, "{} logged out", getCurrentUser().getUsername());
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
        findUserConnection(userId).send(message);
    }

    @PreAuthorize("permitAll()")
    public void returnMessage(Message message) {
        getCurrentConnection().send(message);
    }

    @PreAuthorize("hasRole('ROLE_USER')")
    public UserAccount getCurrentUser() {
        return userService.getUser(getAuthentication(getCurrentConnection()).getCredentials());
    }

    @PreAuthorize("hasRole('ROLE_USER')")
    public Long getCurrentUserId() {
        return getUserId(getCurrentConnection());
    }

    private Long getUserId(HostedConnection connection) {
        DefaultAuthentication<HostedConnection> authentication = getAuthentication(connection);
        return authentication == null ? null : authentication.getCredentials();
    }

    private void attachAuthentication(HostedConnection connection) {
        SecurityContextHolder.getContext().setAuthentication(getAuthentication(connection));
    }

    private void detachAuthentication() {
        SecurityContextHolder.clearContext();
    }

    private void setAnonymous(HostedConnection connection) {
        Long currentUserId = getUserId(connection);
        if (currentUserId != null) {
            eventPublisher.publishEvent(new SystemTaskEvent(new UserLogoutEvent(currentUserId)));
        }
        setAuthentication(connection, new DefaultAuthentication(connection, null, UserRoles.ANONYMOUS));
    }

    private HostedConnection getCurrentConnection() {
        return ((DefaultAuthentication<HostedConnection>) SecurityContextHolder.getContext().getAuthentication()).getPrincipal();
    }

    private void setAuthentication(HostedConnection connection, Authentication authentication) {
        connection.setAttribute(AUTHENTICATION, authentication);
    }

    private DefaultAuthentication<HostedConnection> getAuthentication(HostedConnection connection) {
        return connection.getAttribute(AUTHENTICATION);
    }

    private HostedConnection findUserConnection(long userId) throws NullPointerException {
        for (HostedConnection connection : server.getConnections()) {
            Long id = getAuthentication(connection).getCredentials();
            if (id != null && id == userId) {
                return connection;
            }
        }
        return null;
    }
}