package com.etherblood.cardsmasterserver;

import com.etherblood.cardsmasterserver.cards.CardCollectionService;
import com.etherblood.cardsmasterserver.network.connections.DefaultAuthentication;
import com.etherblood.cardsmasterserver.users.UserRoles;
import com.etherblood.cardsmasterserver.users.UserService;
import com.etherblood.cardsmasterserver.users.model.UserAccount;
import com.etherblood.cardsnetworkshared.master.commands.UserRegistration;
import com.jme3.network.serializing.Serializer;
import generatedSerializable.Serializables;
import java.util.Scanner;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.security.core.context.SecurityContextHolder;

public class Main {

    private static final String SPRING_CONTEXT_FILEPATH = "main_spring_context.xml";

    public static void main(String[] args) throws InterruptedException {
        Serializer.registerClasses(new Serializables().classes);
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
