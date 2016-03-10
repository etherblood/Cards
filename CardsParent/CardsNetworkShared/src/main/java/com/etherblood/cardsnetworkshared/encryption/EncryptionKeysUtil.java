package com.etherblood.cardsnetworkshared.encryption;

import java.math.BigInteger;
import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.RSAPrivateKeySpec;
import java.security.spec.RSAPublicKeySpec;
import javax.crypto.Cipher;

/**
 *
 * @author Philipp
 */
public class EncryptionKeysUtil {
    public static final String RSA = "RSA";
    private static final String SEPERATOR = ";";

    public static RSAKeyPair generateKeys() {
        KeyPairGenerator kpg;
        try {
            kpg = KeyPairGenerator.getInstance(RSA);
        } catch (NoSuchAlgorithmException ex) {
            throw new RuntimeException(ex);
        }
        kpg.initialize(1024);
        KeyPair kp = kpg.genKeyPair();
        return new RSAKeyPair(kp);
    }
    
    public static String exportRSAPublic(RSAPublicKey key) {
        String exp = key.getPublicExponent().toString(16);
        String mod = key.getModulus().toString(16);
        return exp + SEPERATOR + mod;
    }

    public static String exportRSAPrivate(RSAPrivateKey key) {
        String exp = key.getPrivateExponent().toString(16);
        String mod = key.getModulus().toString(16);
        return exp + SEPERATOR + mod;
    }

    public static RSAPublicKey importRSAPublic(String value) throws NoSuchAlgorithmException, InvalidKeySpecException {
        String[] parts = value.split(SEPERATOR);
        String exp = parts[0];
        String mod = parts[1];
        RSAPublicKeySpec pubKeySpec = new RSAPublicKeySpec(new BigInteger(mod, 16), new BigInteger(exp, 16));
        KeyFactory keyFactory = KeyFactory.getInstance(RSA);
        RSAPublicKey pubKey = (RSAPublicKey) keyFactory.generatePublic(pubKeySpec);
        return pubKey;
    }

    public static RSAPrivateKey importRSAPrivate(String value) throws NoSuchAlgorithmException, InvalidKeySpecException {
        String[] parts = value.split(SEPERATOR);
        String exp = parts[0];
        String mod = parts[1];
        RSAPrivateKeySpec privKeySpec = new RSAPrivateKeySpec(new BigInteger(mod, 16), new BigInteger(exp, 16));
        KeyFactory keyFactory = KeyFactory.getInstance(RSA);
        RSAPrivateKey privKey = (RSAPrivateKey) keyFactory.generatePrivate(privKeySpec);
        return privKey;
    }
    

    public static byte[] rsaEncrypt(RSAPublicKey publicKey, byte[] data) throws Exception {
        Cipher cipher = Cipher.getInstance(RSA);
        cipher.init(Cipher.ENCRYPT_MODE, publicKey);
        return cipher.doFinal(data);
    }

    public static byte[] rsaDecrypt(RSAPrivateKey privateKey, byte[] data) throws Exception {
        Cipher cipher = Cipher.getInstance(RSA);
        cipher.init(Cipher.DECRYPT_MODE, privateKey);
        return cipher.doFinal(data);
    }
    
    public static void main(String... args) {
        RSAKeyPair keys = generateKeys();
        System.out.println(exportRSAPrivate(keys.privateKey()));
        System.out.println(exportRSAPublic(keys.publicKey()));
    }
}
