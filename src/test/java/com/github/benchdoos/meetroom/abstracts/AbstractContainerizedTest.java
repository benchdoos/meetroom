package com.github.benchdoos.meetroom.abstracts;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.junit.ClassRule;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.testcontainers.containers.PostgreSQLContainer;

import javax.sql.DataSource;

/**
 * Abstract test class for all containerized tests
 */
public abstract class AbstractContainerizedTest extends AbstractTest {

    @ClassRule
    public static final PostgreSQLContainer container = new PostgreSQLContainer();

    @TestConfiguration
    public static class Config {

        @Bean
        public DataSource dataSource() {
            final HikariConfig hikariConfig = new HikariConfig();
            hikariConfig.setJdbcUrl(container.getJdbcUrl());
            hikariConfig.setDriverClassName(org.postgresql.Driver.class.getCanonicalName());
            hikariConfig.setUsername(container.getUsername());
            hikariConfig.setPassword(container.getPassword());
            return new HikariDataSource(hikariConfig);
        }
    }
}
