package com.etherblood.cardsmasterserver.users.model;

import javax.persistence.Embeddable;
import javax.validation.constraints.NotNull;

/**
 *
 * @author Philipp
 */
@Embeddable
public class Password {
    @NotNull
    private String hash;
    @NotNull
    private String salt;

    public String getHash() {
        return hash;
    }

    public void setHash(String hash) {
        this.hash = hash;
    }

    public String getSalt() {
        return salt;
    }

    public void setSalt(String salt) {
        this.salt = salt;
    }
}
