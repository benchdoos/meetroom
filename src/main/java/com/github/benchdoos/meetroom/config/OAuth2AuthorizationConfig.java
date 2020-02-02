package com.github.benchdoos.meetroom.config;

import com.github.benchdoos.meetroom.config.constants.AuthorizedGrantTypes;
import com.github.benchdoos.meetroom.domain.enumirations.Client;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.stereotype.Component;

@EnableAuthorizationServer
@RequiredArgsConstructor
@Component
public class OAuth2AuthorizationConfig extends AuthorizationServerConfigurerAdapter {

    private final AuthenticationManager authenticationManager;
    private final UserDetailsService userDetailsService;
    private final PasswordEncoder md5PasswordEncoder;
    private final TokenStore tokenStore;

    /**
     * В конфиге должны быть прописаны уже кодированные
     * {@link AuthorizationServerSecurityConfigurer#passwordEncoder(PasswordEncoder)} client-secrets.
     * Данные дефолты предоставлены для понимания, какой секрет нужно прописывать в клиентских приложениях.
     */
    @Value("${secret.web-app:94a02252-7f12-4f5a-9795-af1b3e08ac1a}")
    private String webAppSecret;
    @Value("${token.access.validitySeconds:7200}")
    private int accessTokenValiditySeconds;
    @Value("${token.refresh.validitySeconds:86400}")
    private int refreshTokenValiditySeconds;

    @Override
    public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
        clients.inMemory()
                .withClient(Client.WEB_APP.name().toLowerCase())
                .secret(webAppSecret)
                .scopes(Client.WEB_APP.getScope())
                .authorizedGrantTypes(AuthorizedGrantTypes.PASSWORD, AuthorizedGrantTypes.REFRESH_TOKEN)
                .accessTokenValiditySeconds(accessTokenValiditySeconds)
                .refreshTokenValiditySeconds(refreshTokenValiditySeconds);
    }

    @Override
    public void configure(AuthorizationServerEndpointsConfigurer endpoints) {
        endpoints.tokenStore(tokenStore)
                .userDetailsService(userDetailsService)
                .authenticationManager(authenticationManager);
    }

    @Override
    public void configure(AuthorizationServerSecurityConfigurer security) {
        security.passwordEncoder(md5PasswordEncoder)
                .allowFormAuthenticationForClients();
    }


}
