package com.etherblood.cardsnetworkshared.encryption;

import com.etherblood.cardsnetworkshared.EncryptedMessage;
import com.jme3.network.Message;
import com.jme3.network.serializing.Serializer;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import javax.crypto.Cipher;
import javax.crypto.NoSuchPaddingException;

/**
 *
 * @author Philipp
 */
public class EncryptedMessageSerializer extends Serializer {

    private final Cipher cipher;

    public EncryptedMessageSerializer() throws NoSuchAlgorithmException, NoSuchPaddingException {
        cipher = Cipher.getInstance(EncryptionKeysUtil.RSA);
    }

    //TODO
    @Override
    public EncryptedMessage readObject(ByteBuffer data, Class c) throws IOException {
        return new EncryptedMessage((Message) Serializer.readClassAndObject(data));
        //disabled because it is not working...
//        if (EncryptedMessage.class != c) {
//            throw new IllegalArgumentException(c.getName());
//        }
//        try {
//            EncryptedMessage result = new EncryptedMessage();
//
//            byte[] byteArray = new byte[data.remaining()];
//
//            data.get(byteArray);
//
//            ByteArrayOutputStream out = new ByteArrayOutputStream();
//
//            byte[] tmp = new byte[1024];
//            int read;
//            try (CipherInputStream in = new CipherInputStream(new ByteArrayInputStream(byteArray), cipher)) {
//                while (in.available() > 0 && ((read = in.read(tmp)) > 0)) {
//                    out.write(tmp, 0, read);
//                }
//                out.flush();
//            }
//
//            result.setMessage((Message) Serializer.readClassAndObject(ByteBuffer.wrap(out.toByteArray())));
//            return result;
//        } catch (Exception ex) {
//            throw new RuntimeException(ex);
//        }
    }

    //TODO
    @Override
    public void writeObject(ByteBuffer buffer, Object object) throws IOException {
        Serializer.writeClassAndObject(buffer, ((EncryptedMessage) object).getMessage());
        //disabled because it is not working...
//        if (!(object instanceof EncryptedMessage)) {
//            throw new IllegalArgumentException(object.toString());
//        }
//        Message message = ((EncryptedMessage) object).getMessage();
//
//        ByteBuffer tempBuffer = ByteBuffer.allocate(1024);
//        Serializer.writeClassAndObject(tempBuffer, message);
//        tempBuffer.flip();
//
//        ByteArrayOutputStream byteArrayOutput = new ByteArrayOutputStream();
//        try (CipherOutputStream cipherOutput = new CipherOutputStream(byteArrayOutput, cipher)) {
//            cipherOutput.write(tempBuffer.array(), 0, tempBuffer.limit());
//            cipherOutput.flush();
//        }
//
//        buffer.put(byteArrayOutput.toByteArray());
    }

    public void setPrivateKey(RSAPrivateKey privateKey) throws InvalidKeyException {
        cipher.init(Cipher.DECRYPT_MODE, privateKey);
    }

    public void setPublicKey(RSAPublicKey publicKey) throws InvalidKeyException {
        cipher.init(Cipher.ENCRYPT_MODE, publicKey);
    }

}
