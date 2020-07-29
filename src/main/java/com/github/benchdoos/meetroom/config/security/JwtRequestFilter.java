package com.github.benchdoos.meetroom.config.security;

import com.github.benchdoos.meetroom.config.beans.SpringConfigurationInfoBean;
import com.github.benchdoos.meetroom.config.constants.ApiConstants;
import com.github.benchdoos.meetroom.domain.dto.security.TokenDto;
import com.github.benchdoos.meetroom.service.TokenService;
import com.github.benchdoos.meetroom.service.UserService;
import com.google.common.net.HttpHeaders;
import io.jsonwebtoken.ExpiredJwtException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.lang.NonNull;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

/**
 * Filter for requests. Gets if request is token-based (or JSESSIONID-based) and provides authorization.
 * If token-based - resets headers.
 * If JSESSIONID-based - do nothing
 */
@Slf4j
@RequiredArgsConstructor
@Component
public class JwtRequestFilter extends OncePerRequestFilter {

    private final SpringConfigurationInfoBean springConfigurationInfoBean;
    private final UserService userService;
    private final TokenService tokenService;

    @Override
    protected void doFilterInternal(
            HttpServletRequest request,
            @NonNull HttpServletResponse response,
            @NonNull FilterChain filterChain) throws ServletException, IOException {

        final String requestTokenHeader = request.getHeader(HttpHeaders.AUTHORIZATION);

        if (StringUtils.hasText(requestTokenHeader)) {
            //authorization via JWT token
            doJwtTokenFilter(request, response, requestTokenHeader);
        } else {
            doSessionFilter(request, response);
        }
        filterChain.doFilter(request, response);
    }

    /**
     * Proceed filter for session
     *
     * @param httpServletRequest request
     * @param httpServletResponse response
     */
    private void doSessionFilter(HttpServletRequest httpServletRequest, @NonNull HttpServletResponse httpServletResponse) {
        final HttpSession session = httpServletRequest.getSession();
        if (session != null) {
            final SecurityContext context = (SecurityContext) session.getAttribute("SPRING_SECURITY_CONTEXT");
            if (context != null) {
                final UserDetails principal = (UserDetails) context.getAuthentication().getPrincipal();
                if (principal != null) {
                    final String username = principal.getUsername();
                    if (username != null) {
                        final TokenDto token = tokenService.createToken(userService.loadUserByUsername(username));
                        appendTokenToCookies(token, httpServletResponse);
                    }
                }
            }
        }
    }

    /***
     * Proceed jwt filter for request
     *
     * @param httpServletRequest request
     * @param httpServletResponse response
     * @param requestTokenHeader auth header
     */
    private void doJwtTokenFilter(HttpServletRequest httpServletRequest,
                                  @NonNull HttpServletResponse httpServletResponse,
                                  String requestTokenHeader) {

        String username = null;
        String jwtToken = null;

        // JWT Token is in the form "Bearer token". Remove Bearer word and get
        // only the Token
        if (requestTokenHeader.startsWith(OAuth2AccessToken.BEARER_TYPE + " ")) {
            jwtToken = requestTokenHeader.substring(7);
            try {
                username = tokenService.getUsernameFromToken(jwtToken);
            } catch (final IllegalArgumentException e) {
                log.warn("Unable to get JWT Token. {}", e.getMessage());
            } catch (final ExpiredJwtException e) {
                log.warn("JWT Token has expired. {}", e.getMessage());
            }
        } else {
            log.warn("JWT Token does not begin with Bearer String");
        }

        // Once we get the token validate it.
        if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
            final UserDetails userDetails = this.userService.loadUserByUsername(username);
            // if token is valid configure Spring Security to manually set
            // authentication
            if (tokenService.validateToken(jwtToken, userDetails)) {
                final UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
                        userDetails,
                        null,
                        userDetails.getAuthorities());

                authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(httpServletRequest));
                // After setting the Authentication in the context, we specify
                // that the current user is authenticated. So it passes the
                // Spring Security Configurations successfully.
                SecurityContextHolder.getContext().setAuthentication(authenticationToken);
                // FIXME: 12.04.2020 This is not the best solution I guess. Better solution needed
                //  issue#57
                //  https://github.com/benchdoos/meetroom/issues/57
                httpServletResponse.reset();
            }
        }
    }

    /**
     * Append to response cookies token info
     *
     * @param token to add
     * @param httpServletResponse response
     */
    private void appendTokenToCookies(TokenDto token, HttpServletResponse httpServletResponse) {
        final Cookie tokenCookie = createCookie(
                "token",
                token.getAccessToken().getToken(),
                "Meetroom authorization cookie");

        final Cookie tokenType = createCookie(
                "tokenType",
                token.getAccessToken().getTokenType(),
                "Meetroom authorization cookie type");

        httpServletResponse.addCookie(tokenCookie);
        httpServletResponse.addCookie(tokenType);
    }

    /**
     * Create cookie by key-value pair
     *
     * @param key cookie key
     * @param value cookie value
     * @param comment for cookie
     * @return cookie
     */
    private Cookie createCookie(String key, String value, String comment) {
        final Cookie tokenCookie = new Cookie(key, value);

        tokenCookie.setComment(comment);
        tokenCookie.setPath(springConfigurationInfoBean.getServletContext().getContextPath() + ApiConstants.API_PATH_PREFIX);
        tokenCookie.setHttpOnly(true);
        return tokenCookie;
    }
}
