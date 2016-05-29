package com.etherblood.cardsmasterserver.core;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Version;
import javax.validation.constraints.NotNull;

/**
 *
 * @author Philipp
 */
@MappedSuperclass
public abstract class ImmutableEntity implements Serializable {

    @NotNull
    @Temporal(TemporalType.TIMESTAMP)
    @Column(updatable = false)
    private Date created;

    @PrePersist
    private void onPersist() {
        created = new Date();
    }

    public Date getCreated() {
        return created;
    }
}
