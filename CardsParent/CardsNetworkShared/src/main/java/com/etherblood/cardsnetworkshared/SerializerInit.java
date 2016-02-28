package com.etherblood.cardsnetworkshared;

import com.jme3.network.serializing.Serializable;
import com.jme3.network.serializing.Serializer;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.ArrayList;
import java.util.Scanner;

/**
 *
 * @author Philipp
 */
public class SerializerInit {
    private static final String PRIVATE_KEY_PATH = "/privateKey.txt";
    private static final String PUBLIC_KEY_PATH = "/publicKey.txt";
    private static final String SERIALIZABLES_PATH = "/META-INF/" + Serializable.class.getName() + ".txt";
    
    public static void init() throws NoSuchAlgorithmException, InvalidKeySpecException {
        Serializer.registerClasses(readClasses(SERIALIZABLES_PATH));
        EncryptedObjectSerializer encryptedObjectSerializer = new EncryptedObjectSerializer();
        String privateKey = readKey(PRIVATE_KEY_PATH);
        if(privateKey != null) {
            encryptedObjectSerializer.setPrivateKey(EncryptionKeysUtil.importRSAPrivate(privateKey));
        }
        String publicKey = readKey(PUBLIC_KEY_PATH);
        if(publicKey != null) {
            encryptedObjectSerializer.setPublicKey(EncryptionKeysUtil.importRSAPublic(publicKey));
        }
        Serializer.registerClass(EncryptedObject.class, encryptedObjectSerializer);
    }

    private static Class[] readClasses(String path) {
        ArrayList<Class> classList = new ArrayList<>();
        Scanner scanner = new Scanner(SerializerInit.class.getResourceAsStream(path), StandardCharsets.UTF_8.name());
        while (scanner.hasNext()) {
            String className = scanner.nextLine();
            try {
                classList.add(Class.forName(className));
            } catch (ClassNotFoundException ex) {
                System.err.println("class " + className + " not found");
                ex.printStackTrace(System.err);
            }
        }
        return classList.toArray(new Class[classList.size()]);
    }
    
    private static String readKey(String path) {
        InputStream keyStream = SerializerInit.class.getResourceAsStream(path);
        if(keyStream == null) {
            return null;
        }
        Scanner scanner = new Scanner(keyStream, StandardCharsets.UTF_8.name());
        return scanner.nextLine();
    }
}
