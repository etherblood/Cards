package com.etherblood.cardsjsonclient;

import com.etherblood.cardsnetworkshared.DefaultMessage;
import com.etherblood.cardsnetworkshared.EncryptedObject;
import com.etherblood.cardsnetworkshared.EncryptedObjectSerializer;
import com.etherblood.cardsnetworkshared.EncryptionKeysUtil;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.jme3.network.Client;
import com.jme3.network.Message;
import com.jme3.network.MessageListener;
import com.jme3.network.Network;
import com.jme3.network.serializing.Serializer;
import generatedSerializable.Serializables;
import java.io.IOException;
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
        Serializer.registerClasses(new Serializables().classes);
        EncryptedObjectSerializer encryptedObjectSerializer = new EncryptedObjectSerializer();
        encryptedObjectSerializer.setPublicKey(EncryptionKeysUtil.importRSAPublic("10001;cde9421b0e82e0e179ff3f2b738151ecf51477b74056d0b40c472aca0b90332f8b2ead260049a5ff63b13ae9e47c48efe35a470b110b9f97324d802c647ae88e9ad299e5a5890fb1d4b79803d7d68b7adb1505b38b93c831e9a54ec7ec8b137b5635f4705fd36097dacceb59346d9a84edd86aa96d729d3cfa91feff0e307c99"));
        Serializer.registerClass(EncryptedObject.class, encryptedObjectSerializer);
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
