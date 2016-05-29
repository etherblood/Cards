package com.etherblood.cardsjmeclient.states;

import com.etherblood.cardscontext.Autowire;
import com.etherblood.cardsjmeclient.events.ClientConnectedEvent;
import com.etherblood.cardsjmeclient.events.ClientDisconnectedEvent;
import com.etherblood.cardsjmeclient.events.EventListener;
import com.etherblood.cardsjmeclient.events.Eventbus;
import com.etherblood.cardsjmeclient.events.ExceptionEvent;
import com.etherblood.cardsjmeclient.events.SynchronizedEvent;
import com.etherblood.cardsnetworkshared.DefaultMessage;
import com.etherblood.cardsnetworkshared.EncryptedMessage;
import com.etherblood.cardsnetworkshared.ExtendedDefaultClient;
import com.jme3.network.AbstractMessage;
import com.jme3.network.Client;
import com.jme3.network.ClientStateListener;
import com.jme3.network.ErrorListener;
import com.jme3.network.Message;
import com.jme3.network.MessageListener;
import java.io.IOException;
import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

/**
 *
 * @author Philipp
 */
public class ConnectionState {
    private Client client;
    @Autowire private Eventbus eventbus;
    
    @PostConstruct
    public void init() {
        eventbus.subscribe(AbstractMessage.class, new EventListener<AbstractMessage>() {
            @Override
            public void onEvent(AbstractMessage event) {
                if(event instanceof DefaultMessage && ((DefaultMessage)event).getData() == null) {
                    System.out.println("cake");
                }
                client.send(event);
            }
        });
    }
    
    public void connect(String ipAddress, int port) throws IOException {
        if(client != null) {
            throw new IllegalStateException("tried to open multiple connections.");
        }
        client = ExtendedDefaultClient.connectToServer(ipAddress, port);//Network.connectToServer(ipAddress, PORT);

        final MessageListener<Client> defaultMessageListener = new MessageListener<Client>() {
            @Override
            public void messageReceived(Client source, Message m) {
                DefaultMessage message = (DefaultMessage) m;
                synchedEvent(message.getData());
            }
        };
        final MessageListener<Client> encryptedMessageListener = new MessageListener<Client>() {
            @Override
            public void messageReceived(Client connection, Message message) {
                defaultMessageListener.messageReceived(connection, ((EncryptedMessage) message).getMessage());
            }
        };
        client.addMessageListener(defaultMessageListener, DefaultMessage.class);
        client.addMessageListener(encryptedMessageListener, EncryptedMessage.class);
        client.addErrorListener(new ErrorListener<Client>() {
            @Override
            public void handleError(Client s, Throwable thrwbl) {
                synchedEvent(new ExceptionEvent(thrwbl));
                disconnect();
            }
        });
        client.addClientStateListener(new ClientStateListener() {
            @Override
            public void clientConnected(Client c) {
                synchedEvent(new ClientConnectedEvent());
            }

            @Override
            public void clientDisconnected(Client c, ClientStateListener.DisconnectInfo info) {
                synchedEvent(new ClientDisconnectedEvent(info));
            }
        });
        client.start();
    }
    
    @PreDestroy
    public void disconnect() {
        client.close();
        client = null;
    }
    
    private void synchedEvent(final Object event) {
        eventbus.sendEvent(new SynchronizedEvent() {
            @Override
            public void run() {
                eventbus.sendEvent(event);
            }
        });
    }
    
}
