package com.github.benchdoos.meetroom.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.InMemoryTokenStore;

/**
 * Configuration for token storage
 */
@Configuration
public class TokenStoreConfig {

    @Bean
    public TokenStore tokenStore() {
        //todo this can be changed to redis or jdbc db (better redis)
        return new InMemoryTokenStore();
    }

}
