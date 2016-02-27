package com.etherblood.cardsnetworkshared;

import com.etherblood.cardsnetworkshared.master.commands.UserLogin;
import com.jme3.network.serializing.Serializer;
import java.io.IOException;
import java.nio.ByteBuffer;
import static junit.framework.Assert.assertEquals;
import junit.framework.TestCase;

/**
 *
 * @author Philipp
 */
public class EncryptionTest extends TestCase {

    public static void main(String[] args) throws IOException {
        new EncryptionTest().testEncryptedObjectSerializer();
    }

    public void testEncryptedObjectSerializer() throws IOException {
        RSAKeyPair keys = EncryptionKeysUtil.generateKeys();

        Serializer.registerClasses(new generatedSerializable.Serializables().classes);
        EncryptedObjectSerializer encryptedObjectSerializer = new EncryptedObjectSerializer();
        encryptedObjectSerializer.setPublicKey(keys.publicKey());
        encryptedObjectSerializer.setPrivateKey(keys.privateKey());
        Serializer.registerClass(EncryptedObject.class, encryptedObjectSerializer);

        ByteBuffer buffer = ByteBuffer.allocate(1024);

        UserLogin login = new UserLogin("myname", "mypassword");
        encryptedObjectSerializer.writeObject(buffer, new EncryptedObject(login));
        buffer.flip();
        UserLogin result = (UserLogin) encryptedObjectSerializer.readObject(buffer, EncryptedObject.class).getObject();
        assertEquals(login.getUsername(), result.getUsername());
        assertEquals(login.getPlaintextPassword(), result.getPlaintextPassword());
    }
}
