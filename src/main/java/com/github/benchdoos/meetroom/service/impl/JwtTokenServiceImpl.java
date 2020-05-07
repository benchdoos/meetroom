package com.github.benchdoos.meetroom.service.impl;

import com.github.benchdoos.meetroom.config.properties.ApiSecurityProperties;
import com.github.benchdoos.meetroom.domain.dto.security.TokenDto;
import com.github.benchdoos.meetroom.service.TokenService;
import com.github.benchdoos.meetroom.service.TokenStorageService;
import com.github.benchdoos.meetroom.service.UserService;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

/**
 * Default {@link TokenService} implementation
 * while creating the token -
 * 1. Define  claims of the token, like Issuer, Expiration, Subject, and the ID
 * 2. Sign the JWT using the HS512 algorithm and secret key.
 * 3. According to JWS Compact Serialization(https://tools.ietf.org/html/draft-ietf-jose-json-web-signature-41#section-3.1)
 * compaction of the JWT to a URL-safe string
 */
@RequiredArgsConstructor
@Service
public class JwtTokenServiceImpl implements TokenService {
    /**
     * Default token type value
     */
    private static final String DEFAULT_TOKEN_TYPE = OAuth2AccessToken.BEARER_TYPE;

    private final ApiSecurityProperties apiSecurityProperties;
    private final UserService userService;
    private final TokenStorageService tokenStorageService;

    @Override
    public TokenDto createToken(UserDetails userDetails) {
        final String username = userDetails.getUsername();
        final TokenDto tokenForUser = tokenStorageService.getTokenForUser(username);

        if (tokenForUser != null) {
            if (!isTokenExpired(tokenForUser.getAccessToken().getToken())) {
                return tokenForUser;
            }
            tokenStorageService.removeTokenForUser(username);
        }

        final TokenDto tokenDto = new TokenDto(generateToken(new HashMap<>(), username), null);
        tokenStorageService.storeToken(username, tokenDto);
        return tokenDto;
    }

    @Override
    public UserDetails getUserByToken(String token) {
        return userService.loadUserByUsername(getUsernameFromToken(token));
    }

    @Override
    public String getUsernameFromToken(String token) {
        return getClaimFromToken(token, Claims::getSubject);
    }

    /**
     * Get expiration date from token
     *
     * @param token token
     * @return date of token expire
     */
    private Date getExpirationDateFromToken(String token) {
        return getClaimFromToken(token, Claims::getExpiration);
    }

    /**
     * Get JWT claim from token
     *
     * @param token token
     * @param claimsResolver claimsResolver
     * @param <T> object from claim
     * @return object from claim
     */
    private <T> T getClaimFromToken(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = getAllClaimsFromToken(token);
        return claimsResolver.apply(claims);
    }

    private Claims getAllClaimsFromToken(String token) {
        return Jwts.parser().setSigningKey(apiSecurityProperties.getSecret()).parseClaimsJws(token).getBody();
    }

    @Override
    public boolean validateToken(String token, UserDetails userDetails) {
        final String username = getUsernameFromToken(token);
        return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));
    }

    @Override
    public boolean isTokenExpired(String token) {
        final Date expiration = getExpirationDateFromToken(token);
        return expiration.before(new Date());
    }


    /**
     * Create token from given claims
     *
     * @param claims claims
     * @param subject for token. Usually username
     * @return token info
     */
    private TokenDto.TokenInfoDto generateToken(Map<String, Object> claims, String subject) {
        final Date tokenCreated = new Date(System.currentTimeMillis());
        final Date tokenExpires = new Date(System.currentTimeMillis() + apiSecurityProperties.getTokenValidity() * 1000);

        final String token = Jwts.builder()
                .setClaims(claims)
                .setSubject(subject)
                .setIssuedAt(tokenCreated)
                .setExpiration(tokenExpires)
                .signWith(SignatureAlgorithm.HS512, apiSecurityProperties.getSecret()).compact();

        return new TokenDto.TokenInfoDto(token, DEFAULT_TOKEN_TYPE, tokenCreated, tokenExpires);
    }
}
