package com.etherblood.cardsnetworkshared.encryption;

//package com.etherblood.cardsnetworkshared;
//
//import static com.etherblood.cardsnetworkshared.EncryptionKeysUtil.RSA;
//import com.jme3.network.serializing.Serializer;
//import java.io.IOException;
//import java.nio.ByteBuffer;
//import java.security.interfaces.RSAPrivateKey;
//import java.security.interfaces.RSAPublicKey;
//import javax.crypto.Cipher;
//
///**
// *
// * @author Philipp
// */
//public class EncryptedObjectSerializer extends Serializer {
//
//    private RSAPublicKey publicKey;
//    private RSAPrivateKey privateKey;
//
//    @Override
//    public EncryptedObject readObject(ByteBuffer data, Class c) throws IOException {
//        try {
//            byte[] encryptedData = Serializer.getSerializer(byte[].class).readObject(data, byte[].class);
//            byte[] decryptedData = rsaDecrypt(encryptedData);
//            return new EncryptedObject(Serializer.readClassAndObject(ByteBuffer.wrap(decryptedData)));
//        } catch (Exception ex) {
//            throw new RuntimeException(ex);
//        }
//    }
//
//    @Override
//    public void writeObject(ByteBuffer buffer, Object object) throws IOException {
//        try {
//            EncryptedObject encryptedObject = (EncryptedObject) object;
//            ByteBuffer tmpBuffer = ByteBuffer.allocate(117);
//            Serializer.writeClassAndObject(tmpBuffer, encryptedObject.getObject());
//            tmpBuffer.flip();
//            byte[] decryptedData = new byte[tmpBuffer.remaining()];
//            tmpBuffer.get(decryptedData);
//            byte[] encryptedData = rsaEncrypt(decryptedData);
//            Serializer.getSerializer(byte[].class).writeObject(buffer, encryptedData);
//        } catch (Exception ex) {
//            throw new RuntimeException(ex);
//        }
//    }
//
//    private byte[] rsaEncrypt(byte[] data) throws Exception {
//        Cipher cipher = Cipher.getInstance(RSA);
//        cipher.init(Cipher.ENCRYPT_MODE, publicKey);
//        return cipher.doFinal(data);
//    }
//
//    private byte[] rsaDecrypt(byte[] data) throws Exception {
//        Cipher cipher = Cipher.getInstance(RSA);
//        cipher.init(Cipher.DECRYPT_MODE, privateKey);
//        return cipher.doFinal(data);
//    }
//
//    public RSAPrivateKey getPrivateKey() {
//        return privateKey;
//    }
//
//    public void setPrivateKey(RSAPrivateKey privateKey) {
//        this.privateKey = privateKey;
//    }
//
//    public RSAPublicKey getPublicKey() {
//        return publicKey;
//    }
//
//    public void setPublicKey(RSAPublicKey publicKey) {
//        this.publicKey = publicKey;
//    }
//    
//}
