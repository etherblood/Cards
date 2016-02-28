package com.etherblood.cardsjsonclient;

import com.etherblood.cardsnetworkshared.DefaultMessage;
import com.etherblood.cardsnetworkshared.EncryptedObject;
import com.etherblood.cardsnetworkshared.EncryptedObjectSerializer;
import com.etherblood.cardsnetworkshared.EncryptionKeysUtil;
import com.etherblood.cardsnetworkshared.SerializerInit;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.jme3.network.Client;
import com.jme3.network.Message;
import com.jme3.network.MessageListener;
import com.jme3.network.Network;
import com.jme3.network.serializing.Serializer;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.Scanner;

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

    public static void main(String[] args) throws NoSuchAlgorithmException, InvalidKeySpecException {
        SerializerInit.init();
        try {
            String ipAddress = args.length != 0 ? args[0] : "localhost";
            client = Network.connectToServer(ipAddress, PORT);
        } catch (IOException ex) {
            throw new RuntimeException(ex);
        }

        client.addMessageListener(new MessageListener<Client>() {
            @Override
            public void messageReceived(Client source, Message m) {
                DefaultMessage message = (DefaultMessage) m;
                Object data = message.getData();
                if (data instanceof EncryptedObject) {
                    data = ((EncryptedObject)data).getObject();
                }
                System.out.println(data.getClass().getName() + NEW_LINE + GSON.toJson(data));
            }
        }, DefaultMessage.class);
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
            if(encrypted) {
                data = new EncryptedObject(data);
            }
            client.send(new DefaultMessage(data));
        } catch (Exception e) {
            e.printStackTrace(System.err);
        }
    }
}
