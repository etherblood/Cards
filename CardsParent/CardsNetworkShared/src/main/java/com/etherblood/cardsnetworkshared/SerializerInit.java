package com.etherblood.cardsnetworkshared;

import com.etherblood.cardsnetworkshared.encryption.EncryptionKeysUtil;
import com.etherblood.cardsnetworkshared.encryption.EncryptedMessageSerializer;
import com.jme3.network.serializing.Serializable;
import com.jme3.network.serializing.Serializer;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.ArrayList;
import java.util.Scanner;
import javax.crypto.NoSuchPaddingException;

/**
 *
 * @author Philipp
 */
public class SerializerInit {
    private static final String PRIVATE_KEY_PATH = "/privateKey.txt";
    private static final String PUBLIC_KEY_PATH = "/publicKey.txt";
    private static final String SERIALIZABLES_PATH = "/META-INF/" + Serializable.class.getName() + ".txt";
    
    public static void init() throws NoSuchAlgorithmException, InvalidKeySpecException, NoSuchPaddingException, InvalidKeyException {
        Serializer.registerClasses(readClasses(SERIALIZABLES_PATH));
        EncryptedMessageSerializer encryptedMessageSerializer = new EncryptedMessageSerializer();
        String privateKey = readKey(PRIVATE_KEY_PATH);
        if(privateKey != null) {
            encryptedMessageSerializer.setPrivateKey(EncryptionKeysUtil.importRSAPrivate(privateKey));
        }
        String publicKey = readKey(PUBLIC_KEY_PATH);
        if(publicKey != null) {
            encryptedMessageSerializer.setPublicKey(EncryptionKeysUtil.importRSAPublic(publicKey));
        }
        Serializer.registerClass(EncryptedMessage.class, encryptedMessageSerializer);
        Serializer.setReadOnly(true);
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
