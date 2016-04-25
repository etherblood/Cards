package com.etherblood.cardsmasterserver;

import com.etherblood.cardsnetworkshared.DefaultMessage;
import com.etherblood.cardsnetworkshared.EncryptedMessage;
import com.etherblood.cardsnetworkshared.encryption.EncryptedMessageSerializer;
import com.etherblood.cardsnetworkshared.encryption.EncryptionKeysUtil;
import com.etherblood.cardsnetworkshared.encryption.RSAKeyPair;
import com.etherblood.cardsnetworkshared.SerializerInit;
import com.etherblood.cardsnetworkshared.master.commands.UserLogin;
import com.jme3.network.Message;
import com.jme3.network.serializing.Serializer;
import java.nio.ByteBuffer;
import static junit.framework.Assert.assertEquals;
import junit.framework.TestCase;

/**
 *
 * @author Philipp
 */
public class EncryptionTest extends TestCase {

    public static void main(String[] args) throws Exception {
        new EncryptionTest().testEncryptedObjectSerializer();
    }

    public void testEncryptedObjectSerializer() throws Exception {
        SerializerInit.init();
        RSAKeyPair keys = EncryptionKeysUtil.generateKeys();
        EncryptedMessageSerializer encryptedObjectSerializer = (EncryptedMessageSerializer) Serializer.getExactSerializer(EncryptedMessage.class);
        encryptedObjectSerializer.setPublicKey(keys.publicKey());
        encryptedObjectSerializer.setPrivateKey(keys.privateKey());

        ByteBuffer buffer = ByteBuffer.allocate(1024);

        UserLogin login = new UserLogin("myname", "mypassword");
        encryptedObjectSerializer.writeObject(buffer, new EncryptedMessage(new DefaultMessage(login)));
        buffer.flip();
        final Message message = encryptedObjectSerializer.readObject(buffer, EncryptedMessage.class).getMessage();
        UserLogin result = ((DefaultMessage<UserLogin>)message).getData();
        assertEquals(login.getUsername(), result.getUsername());
        assertEquals(login.getPlaintextPassword(), result.getPlaintextPassword());
    }
}
