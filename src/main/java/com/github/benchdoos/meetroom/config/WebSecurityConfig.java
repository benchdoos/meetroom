package com.github.benchdoos.meetroom.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@RequiredArgsConstructor
@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {

        //public pages
        http.authorizeRequests()
                .antMatchers(
                        "/",
                        "/login",
                        "/registration",
                        "/user/reset-password/**").permitAll();

        //public additional sources
        http.authorizeRequests()
                .antMatchers(
                        "/css/**",
                        "/images/**",
                        "/js/**",
                        "/webjars/**").permitAll();

        //public internal pages
        http.authorizeRequests()
                .antMatchers(
                        "/actuator/**",
                        "/csrf").permitAll();

        //public rest pages //fixme: not working
        http.authorizeRequests()
                .antMatchers(
                        "/auth/token").permitAll();

        //Login form
        http.authorizeRequests()
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
                .httpBasic();
    }
}