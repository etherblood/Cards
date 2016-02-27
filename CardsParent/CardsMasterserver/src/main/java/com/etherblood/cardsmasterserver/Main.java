package com.etherblood.cardsmasterserver;

import com.etherblood.cardsmasterserver.cards.CardCollectionService;
import com.etherblood.cardsmasterserver.network.connections.DefaultAuthentication;
import com.etherblood.cardsmasterserver.users.UserRoles;
import com.etherblood.cardsmasterserver.users.UserService;
import com.etherblood.cardsmasterserver.users.model.UserAccount;
import com.etherblood.cardsnetworkshared.EncryptedObject;
import com.etherblood.cardsnetworkshared.EncryptionKeysUtil;
import com.etherblood.cardsnetworkshared.EncryptedObjectSerializer;
import com.etherblood.cardsnetworkshared.master.commands.UserRegistration;
import com.jme3.network.serializing.Serializer;
import generatedSerializable.Serializables;
import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.Scanner;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.security.core.context.SecurityContextHolder;

public class Main {

    private static final String SPRING_CONTEXT_FILEPATH = "main_spring_context.xml";

    public static void main(String[] args) throws InterruptedException, NoSuchAlgorithmException, InvalidKeySpecException, IOException {
        Serializer.registerClasses(new Serializables().classes);
        EncryptedObjectSerializer encryptedObjectSerializer = new EncryptedObjectSerializer();
        String privateKey = "9c44df577b5ed65e28610f46e1a50e50dffa85947c6ea67b4e2a208e2fd263163cf1d29459798b52540d6821a3b6b2ffe3b25ae24a306110b52b5e0fd2bdf8890cdb3f7b3c162a540d6d71c2aa99220a620cfa1514537935a67605960dd5766d7193ad9354d0a06cce8fba69890515802612533b33e068dba1943d5385454a81;cde9421b0e82e0e179ff3f2b738151ecf51477b74056d0b40c472aca0b90332f8b2ead260049a5ff63b13ae9e47c48efe35a470b110b9f97324d802c647ae88e9ad299e5a5890fb1d4b79803d7d68b7adb1505b38b93c831e9a54ec7ec8b137b5635f4705fd36097dacceb59346d9a84edd86aa96d729d3cfa91feff0e307c99";
        encryptedObjectSerializer.setPrivateKey(EncryptionKeysUtil.importRSAPrivate(privateKey));
        Serializer.registerClass(EncryptedObject.class, encryptedObjectSerializer);
        new Main().run();
    }

    private void run() {
        try (ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext(SPRING_CONTEXT_FILEPATH)) {
            context.registerShutdownHook();
            DefaultAuthentication adminAuthentication = new DefaultAuthentication(DefaultAuthentication.LOCAL_ADMIN_PRINCIPAL, null, UserRoles.ADMIN);
            SecurityContextHolder.getContext().setAuthentication(adminAuthentication);
            runAdminThread(context);
            SecurityContextHolder.clearContext();
        }
    }

    private void runAdminThread(ClassPathXmlApplicationContext context) {
        context.getBean(UserService.class).registerUser(new UserRegistration("testuser", "password"));
        Scanner scanner = new Scanner(System.in);
        while (true) {
            String nextLine = scanner.nextLine();
            try {
                String[] args = nextLine.split(" ");
                switch (args[0]) {
                    case "q":
                    case "exit":
                    case "shutdown":
                        return;
                    case "register":
                        context.getBean(UserService.class).registerUser(new UserRegistration(args[1], chainArgs(args, 2)));
                        break;
                    case "gift":
                        UserAccount user = context.getBean(UserService.class).getUser(args[1]);
                        context.getBean(CardCollectionService.class).giftCard(user, chainArgs(args, 2), 1);
                        break;
                    default:
                        System.out.println("command not found");
                        break;
                }
            } catch (Exception e) {
                e.printStackTrace(System.out);
            }
        }
    }

    private String chainArgs(String[] args, int from) {
        String result = args[from];
        for (int i = from + 1; i < args.length; i++) {
            result += " " + args[i];
        }
        return result;
    }
}
