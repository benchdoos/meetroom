package com.github.benchdoos.meetroom.config.hibernate;

import org.hibernate.dialect.PostgreSQL10Dialect;

import java.sql.Types;

public class JsonBPostgresDialect extends PostgreSQL10Dialect {
    public JsonBPostgresDialect() {
        super();
        this.registerColumnType(Types.JAVA_OBJECT, "jsonb");

    }
}
