package com.etherblood.cardsnetworkshared.encryption;

import java.security.KeyPair;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;

/**
 *
 * @author Philipp
 */
public class RSAKeyPair {

    private final KeyPair pair;

    RSAKeyPair(KeyPair pair) {
        this.pair = pair;
    }

    public RSAPublicKey publicKey() {
        return (RSAPublicKey) pair.getPublic();
    }

    public RSAPrivateKey privateKey() {
        return (RSAPrivateKey) pair.getPrivate();
    }
}
