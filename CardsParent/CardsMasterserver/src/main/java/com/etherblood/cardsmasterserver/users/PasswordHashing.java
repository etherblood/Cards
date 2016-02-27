package com.etherblood.cardsmasterserver.users;

import java.security.spec.KeySpec;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import javax.xml.bind.DatatypeConverter;

/**
 *
 * @author Philipp
 */
public class PasswordHashing {
    private static final String HASH_ALGORITHM = "PBKDF2WithHmacSHA1";
    
    public String hashPassword(String plaintextPassword, String salt) throws Exception {
        byte[] saltArray = DatatypeConverter.parseHexBinary(salt);
        byte[] passwordHash = hashPassword(plaintextPassword, saltArray);
        return DatatypeConverter.printHexBinary(passwordHash);
    }

    private byte[] hashPassword(String password, byte[] salt) throws Exception {
        KeySpec spec = new PBEKeySpec(password.toCharArray(), salt, 65536, 128);
        SecretKeyFactory factory = SecretKeyFactory.getInstance(HASH_ALGORITHM);
        return factory.generateSecret(spec).getEncoded();
    }
}
