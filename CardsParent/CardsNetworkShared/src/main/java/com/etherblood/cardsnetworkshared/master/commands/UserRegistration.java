package com.etherblood.cardsnetworkshared.master.commands;

import com.jme3.network.serializing.Serializable;

/**
 *
 * @author Philipp
 */
@Serializable
public class UserRegistration {

    private String username, plaintextPassword;

    public UserRegistration() {
    }

    public UserRegistration(String username, String plaintextPassword) {
        this.username = username;
        this.plaintextPassword = plaintextPassword;
    }

    public String getUsername() {
        return username;
    }

    public String getPlaintextPassword() {
        return plaintextPassword;
    }
}
