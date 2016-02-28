package com.etherblood.cardsmasterserver;

import com.etherblood.cardsnetworkshared.EncryptedObject;
import com.etherblood.cardsnetworkshared.EncryptedObjectSerializer;
import com.etherblood.cardsnetworkshared.EncryptionKeysUtil;
import com.etherblood.cardsnetworkshared.RSAKeyPair;
import com.etherblood.cardsnetworkshared.SerializerInit;
import com.etherblood.cardsnetworkshared.master.commands.UserLogin;
import com.jme3.network.serializing.Serializer;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import static junit.framework.Assert.assertEquals;
import junit.framework.TestCase;

/**
 *
 * @author Philipp
 */
public class EncryptionTest extends TestCase {

    public static void main(String[] args) throws IOException, NoSuchAlgorithmException, InvalidKeySpecException {
        new EncryptionTest().testEncryptedObjectSerializer();
    }

    public void testEncryptedObjectSerializer() throws IOException, NoSuchAlgorithmException, InvalidKeySpecException {
        SerializerInit.init();
        RSAKeyPair keys = EncryptionKeysUtil.generateKeys();
        EncryptedObjectSerializer encryptedObjectSerializer = (EncryptedObjectSerializer) Serializer.getExactSerializer(EncryptedObject.class);
        encryptedObjectSerializer.setPublicKey(keys.publicKey());
        encryptedObjectSerializer.setPrivateKey(keys.privateKey());

        ByteBuffer buffer = ByteBuffer.allocate(1024);

        UserLogin login = new UserLogin("myname", "mypassword");
        encryptedObjectSerializer.writeObject(buffer, new EncryptedObject(login));
        buffer.flip();
        UserLogin result = (UserLogin) encryptedObjectSerializer.readObject(buffer, EncryptedObject.class).getObject();
        assertEquals(login.getUsername(), result.getUsername());
        assertEquals(login.getPlaintextPassword(), result.getPlaintextPassword());
    }
}
