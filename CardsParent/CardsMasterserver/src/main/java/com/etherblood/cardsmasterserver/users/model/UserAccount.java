package com.etherblood.cardsmasterserver.users.model;

import com.etherblood.cardsmasterserver.core.MutableEntity;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;

/**
 *
 * @author Philipp
 */
@Entity
public class UserAccount extends MutableEntity {

    @Id
    @GeneratedValue
    private Long id;
    @Column(unique=true, nullable=false)
    private String username;
    @NotNull
    private String plaintextPassword;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPlaintextPassword() {
        return plaintextPassword;
    }

    public void setPlaintextPassword(String plaintextPassword) {
        this.plaintextPassword = plaintextPassword;
    }

}
