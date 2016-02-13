package com.etherblood.cardsnetworkshared.master.commands;

import com.jme3.network.serializing.Serializable;

/**
 *
 * @author Philipp
 */
@Serializable
public class UserLogin {

    private String username, plaintextPassword;

    public UserLogin() {
    }

    public UserLogin(String username, String plaintextPassword) {
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
