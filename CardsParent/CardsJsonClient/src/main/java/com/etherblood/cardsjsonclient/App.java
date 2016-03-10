package com.etherblood.cardsjsonclient;

import com.etherblood.cardsnetworkshared.DefaultMessage;
import com.etherblood.cardsnetworkshared.EncryptedMessage;
import com.etherblood.cardsnetworkshared.ExtendedDefaultClient;
import com.etherblood.cardsnetworkshared.SerializerInit;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonSyntaxException;
import com.jme3.network.Client;
import com.jme3.network.Message;
import com.jme3.network.MessageListener;
import com.jme3.network.Network;
import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.Scanner;
import javax.crypto.NoSuchPaddingException;

/**
 * Hello world!
 *
 */
public class App {

    private static final int PORT = 6145;
    private static final String NEW_LINE = System.lineSeparator();
    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().disableHtmlEscaping().create();
    private static Client client;
    public static final String COMMAND_END = ";";

    public static void main(String[] args) throws NoSuchAlgorithmException, InvalidKeySpecException, NoSuchPaddingException, InvalidKeyException {
        SerializerInit.init();
        try {
            String ipAddress = args.length != 0 ? args[0] : "localhost";
            client = ExtendedDefaultClient.connectToServer(ipAddress, PORT);//Network.connectToServer(ipAddress, PORT);
        } catch (IOException ex) {
            throw new RuntimeException(ex);
        }
        
        final MessageListener<Client> defaultMessageListener = new MessageListener<Client>() {
            @Override
            public void messageReceived(Client connection, Message message) {
                Object data = ((DefaultMessage) message).getData();
                System.out.println(data.getClass().getName() + NEW_LINE + GSON.toJson(data));
            }
        };
        final MessageListener<Client> encryptedMessageListener = new MessageListener<Client>() {
            @Override
            public void messageReceived(Client connection, Message message) {
                defaultMessageListener.messageReceived(connection, ((EncryptedMessage)message).getMessage());
            }
        };
        client.addMessageListener(defaultMessageListener, DefaultMessage.class);
        client.addMessageListener(encryptedMessageListener, EncryptedMessage.class);
        client.start();

        Scanner scanner = new Scanner(System.in);
        StringBuilder document = new StringBuilder();
        while (true) {
            String nextLine = scanner.nextLine();
            int oldLength = document.length();
            document.append(nextLine);
            int index = document.indexOf(COMMAND_END, oldLength);
            while (index != -1) {
                String command = document.substring(0, index);
                executeCommand(command.trim());
                document.delete(0, index + 1);
                index = document.indexOf(COMMAND_END);
            }
        }
    }

    private static void executeCommand(String command) {
        try {
            if ("exit".equals(command)) {
                System.exit(0);
                return;
            }
            boolean encrypted = command.startsWith("encrypt ");
            if(encrypted) {
                command = command.substring("encrypt ".length());
            }
            int start = command.indexOf('{');
            String className = command.substring(0, start).trim();
            Class type = Class.forName(className);
            String json = command.substring(start);
            Object data = GSON.fromJson(json, type);
            Message message = new DefaultMessage(data);
            if(encrypted) {
                message = new EncryptedMessage(message);
            }
            client.send(message);
        } catch (ClassNotFoundException | JsonSyntaxException e) {
            e.printStackTrace(System.err);
        }
    }
}
