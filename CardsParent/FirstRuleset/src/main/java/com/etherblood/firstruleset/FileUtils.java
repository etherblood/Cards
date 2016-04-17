package com.etherblood.firstruleset;

import java.io.InputStream;
import java.util.Scanner;
import java.util.function.Consumer;

/**
 *
 * @author Philipp
 */
public class FileUtils {
    
    public static void foreachClass(InputStream input, String charset, Consumer<Class> consumer) {
        foreachLine(input, charset, (String className) -> {
            try {
                consumer.accept(Class.forName(className));
            } catch (ClassNotFoundException ex) {
                System.err.println("class " + className + " not found");
                ex.printStackTrace(System.err);
            }
        });
    }
    
    public static void foreachLine(InputStream input, String charset, Consumer<String> consumer) {
        try (Scanner scanner = new Scanner(input, charset)) {
            while (scanner.hasNext()) {
                String line = scanner.nextLine().trim();
                if (line.startsWith("#")) {
                    continue;
                }
                consumer.accept(line);
            }
        }
    }
}
