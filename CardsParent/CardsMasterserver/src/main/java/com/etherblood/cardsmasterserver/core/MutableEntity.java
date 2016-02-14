package com.etherblood.cardsmasterserver.core;

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
public abstract class MutableEntity {

    @NotNull
    @Temporal(TemporalType.TIMESTAMP)
    @Column(updatable = false)
    private Date created;
    @Version
    private int version;
    @NotNull
    @Temporal(TemporalType.TIMESTAMP)
    private Date updated;

    @PreUpdate
    private void onUpdate() {
        updated = new Date();
    }

    @PrePersist
    private void onPersist() {
        final Date now = new Date();
//        if (created == null) { // Tests could have modified it
            created = now;
//        }
        updated = now;
    }

    public Date getCreated() {
        return created;
    }

    public int getVersion() {
        return version;
    }

    public Date getUpdated() {
        return updated;
    }

    public void setCreated(Date created) {
        this.created = created;
    }

    public void setVersion(int version) {
        this.version = version;
    }

    public void setUpdated(Date updated) {
        this.updated = updated;
    }
}
