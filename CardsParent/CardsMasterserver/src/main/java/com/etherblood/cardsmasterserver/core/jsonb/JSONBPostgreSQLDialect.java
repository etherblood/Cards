package com.etherblood.cardsmasterserver.core.jsonb;

import org.hibernate.dialect.PostgreSQL9Dialect;

import java.sql.Types;

/**
 * Wrap default PostgreSQL9Dialect with 'jsonb' type.
 *
 * @author timfulmer
 */
public class JSONBPostgreSQLDialect extends PostgreSQL9Dialect {

    public JSONBPostgreSQLDialect() {

        super();

        this.registerColumnType(Types.JAVA_OBJECT, "jsonb");
    }
}