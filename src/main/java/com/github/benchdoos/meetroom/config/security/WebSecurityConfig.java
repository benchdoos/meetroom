package com.github.benchdoos.meetroom.config.security;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

/**
 * Default implement of {@link WebSecurityConfigurerAdapter}
 */
@RequiredArgsConstructor
@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
    private final JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;
    private final JwtRequestFilter jwtRequestFilter;

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Override
    protected void configure(HttpSecurity httpSecurity) throws Exception {

        //public pages
        httpSecurity.authorizeRequests()
                .antMatchers(
                        "/",
                        "/login",
                        "/registration",
                        "/user/reset-password/**",
                        "/user/activate/**",
                        "/user/submit-email-update/**"
                ).permitAll();

        //public methods for api
        httpSecurity.authorizeRequests()
                .antMatchers("/api/v1/public/**").permitAll();

        //favicon
        httpSecurity.authorizeRequests()
                .antMatchers("/favicon.ico").permitAll();

        //public additional sources
        httpSecurity.authorizeRequests()
                .antMatchers(
                        "/css/**",
                        "/images/**",
                        "/js/**",
                        "/webjars/**").permitAll();

        //public internal pages
        httpSecurity.authorizeRequests()
                .antMatchers(
                        "/actuator/**",
                        "/csrf").permitAll();

        //public rest pages
        httpSecurity.authorizeRequests()
                .antMatchers(
                        "/auth/token").permitAll();

        httpSecurity.addFilterBefore(jwtRequestFilter, UsernamePasswordAuthenticationFilter.class);

        //Login form
        httpSecurity.authorizeRequests()
                .anyRequest().authenticated()
                .and()
                .formLogin()
                .loginPage("/login")
                .loginProcessingUrl("/login")
                .defaultSuccessUrl("/")
                .failureUrl("/login?error=true")
                .and()
                .logout()
                .and()
                .exceptionHandling()
                // FIXME: 12.04.2020 this is probably needed, but breaks login page redirect on accessing the
                //  secured page without authorization
//                .authenticationEntryPoint(jwtAuthenticationEntryPoint)
                // FIXME: 12.04.2020
                //  issue#57
                //  maybe STATELESS is able for controller?
                .and()
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.ALWAYS)
                .and()
                .httpBasic()
                .and()
                .csrf().disable();
    }
}