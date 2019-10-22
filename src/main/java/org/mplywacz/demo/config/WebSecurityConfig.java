package org.mplywacz.demo.config;
/*
Author: BeGieU
Date: 13.10.2019
*/

import org.mplywacz.demo.services.AppUserService;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    private final AppUserService appUserService;
    private final AuthenticationFailedEntryPoint authenticationFailedEntryPoint;


    public WebSecurityConfig(AppUserService appUserService,
                             AuthenticationFailedEntryPoint authenticationFailedEntryPoint) {
        this.appUserService = appUserService;
        this.authenticationFailedEntryPoint = authenticationFailedEntryPoint;
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .csrf().disable()
                .authorizeRequests()
                .anyRequest().authenticated()
                .and()
                .httpBasic().authenticationEntryPoint(authenticationFailedEntryPoint);

    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(appUserService); //user service allows spring to get users from db
    }

    /* To allow Pre-flight [OPTIONS] request from browser */
    @Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring().antMatchers(HttpMethod.OPTIONS, "/**");
    }

}
