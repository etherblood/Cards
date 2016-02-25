package com.etherblood.cardsmasterclient;

import com.etherblood.cardsnetworkshared.match.misc.CardsMessage;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonSyntaxException;
import com.jme3.network.Client;
import com.jme3.network.Message;
import com.jme3.network.MessageListener;
import com.jme3.network.Network;
import com.jme3.network.serializing.Serializer;
import generatedSerializable.Serializables;
import java.io.IOException;
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

    public static void main(String[] args) {
        Serializer.registerClasses(new Serializables().classes);
        try {
            String ipAddress = args.length != 0 ? args[0] : "localhost";
            client = Network.connectToServer(ipAddress, PORT);
        } catch (IOException ex) {
            throw new RuntimeException(ex);
        }

        client.addMessageListener(new MessageListener<Client>() {
            @Override
            public void messageReceived(Client source, Message m) {
                CardsMessage message = (CardsMessage) m;
                Object data = message.getData();
                System.out.println(data.getClass() + NEW_LINE + GSON.toJson(data));
            }
        }, CardsMessage.class);
        client.start();

        Scanner scanner = new Scanner(System.in);
        StringBuilder document = new StringBuilder();
        while (true) {
            String nextLine = scanner.nextLine();
            int oldLength = document.length();
            document.append(nextLine);
            int index = document.indexOf(";", oldLength);
            while(index != -1) {
                String command = document.substring(0, index);
                executeCommand(command.trim());
                document.delete(0, index + 1);
                index = document.indexOf(";");
            }
        }
    }

    private static void executeCommand(String command) {
        if ("exit".equals(command)) {
            System.exit(0);
        }
        try {
            int start = command.indexOf('{');
            String className = command.substring(0, start).trim();
            Class type = Class.forName(className);
            String json = command.substring(start);
            Object data = GSON.fromJson(json, type);
            client.send(new CardsMessage(data));
        } catch (ClassNotFoundException | JsonSyntaxException e) {
            e.printStackTrace(System.err);
        }
    }
}
