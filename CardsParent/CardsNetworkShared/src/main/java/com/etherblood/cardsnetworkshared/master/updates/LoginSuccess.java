package com.etherblood.cardsnetworkshared.master.updates;

import com.jme3.network.serializing.Serializable;

/**
 *
 * @author Philipp
 */
@Serializable
public class LoginSuccess {

    private String username;

    public LoginSuccess() {
    }

    public LoginSuccess(String username) {
        this.username = username;
    }

    public String getUsername() {
        return username;
    }
}
