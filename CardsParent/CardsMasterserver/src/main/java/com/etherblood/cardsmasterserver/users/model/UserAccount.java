package com.etherblood.cardsmasterserver.users.model;

import com.etherblood.cardsmasterserver.core.MutableEntity;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Embedded;
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
    @Column(unique = true, nullable = false)
    private String username;
    @NotNull
    @Embedded
    private Password password;
    @NotNull
    @ElementCollection
    private Set<String> roles;

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

    public Password getPassword() {
        return password;
    }

    public void setPassword(Password password) {
        this.password = password;
    }

    public String[] getRolesAsArray() {
        return getRoles().toArray(new String[getRoles().size()]);
    }

    public Set<String> getRoles() {
        if(roles == null) {
            roles = new HashSet<>();
        }
        return roles;
    }
}
