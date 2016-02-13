package com.etherblood.cardsmasterserver.core.jsonb;

import java.util.Objects;

/**
 *
 * @author Philipp
 */
public class JSONBCard {

    public JSONBCard(String templateId) {
        this.templateId = templateId;
    }
    public String templateId;

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 97 * hash + Objects.hashCode(this.templateId);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final JSONBCard other = (JSONBCard) obj;
        if (!Objects.equals(this.templateId, other.templateId)) {
            return false;
        }
        return true;
    }
}
