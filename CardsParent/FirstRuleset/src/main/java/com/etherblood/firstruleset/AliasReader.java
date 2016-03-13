package com.etherblood.firstruleset;

import com.etherblood.cardsmatch.cardgame.components.ComponentAlias;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Scanner;

/**
 *
 * @author Philipp
 */
public class AliasReader {
    private static final String ALIASES_PATH = "/META-INF/" + ComponentAlias.class.getName() + ".txt";
    private static HashMap<String, Class> aliases;

    public static HashMap<String, Class> getAliases() {
        if(aliases == null) {
            aliases = new HashMap<>();
        for (Class aliasClass : readClasses(ALIASES_PATH)) {
            ComponentAlias alias = (ComponentAlias) aliasClass.getAnnotation(ComponentAlias.class);
            aliases.put(alias.name(), aliasClass);
        }
        }
        return aliases;
    }

    private static Class[] readClasses(String path) {
        ArrayList<Class> classList = new ArrayList<>();
//        try {
//            InputStream res = AliasReader.class.getResourceAsStream(path);
//            Enumeration<URL> resources = AliasReader.class.getClassLoader().getResources(path);
//            while (resources.hasMoreElements()) {
//                URL resource = resources.nextElement();
                Scanner scanner;
                scanner = new Scanner(AliasReader.class.getResourceAsStream(path), StandardCharsets.UTF_8.name());
                while (scanner.hasNext()) {
                    String className = scanner.nextLine();
                    try {
                        classList.add(Class.forName(className));
                    } catch (ClassNotFoundException ex) {
                        System.err.println("class " + className + " not found");
                        ex.printStackTrace(System.err);
                    }
                }
//            }
//        } catch (IOException ex) {
//            throw new RuntimeException(ex);
//        }
        return classList.toArray(new Class[classList.size()]);
    }
}
