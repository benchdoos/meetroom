package com.github.benchdoos.meetroom.abstracts;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.junit.After;
import org.junit.Before;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;

import java.util.Locale;

/**
 * Abstract test class for all tests
 */
@Slf4j
public abstract class AbstractTest {
    /**
     * {@link ObjectMapper} for tests that use Spring-contexts
     */
    private ObjectMapper objectMapper;

    @Before
    public abstract void init() throws Exception;

    @After
    public abstract void tearDown() throws Exception;

    /**
     * Get {@link ObjectMapper} if test doesn't initialize Spring-context.
     * For example in Unit-tests.
     * If test uses Spring-context us {@link #objectMapper} instead.
     *
     * @return sanity-сконфигурированный {@link ObjectMapper}
     */
    protected ObjectMapper getSimpleObjectMapper() {
        return Jackson2ObjectMapperBuilder
                .json()
                .autoDetectGettersSetters(true)
                .autoDetectFields(true)
                .failOnUnknownProperties(true)
                .locale(Locale.ENGLISH)
                .build();
    }
}
