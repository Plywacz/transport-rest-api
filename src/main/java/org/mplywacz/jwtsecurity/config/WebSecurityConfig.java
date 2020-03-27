package org.mplywacz.jwtsecurity.config;
/*
Author: BeGieU
Date: 26.11.2019
*/

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
    private final JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;

    private final UserDetailsService jwtUserDetailsService;

    private final JwtRequestFilter jwtRequestFilter;

    public WebSecurityConfig(JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint,
                             UserDetailsService jwtUserDetailsService,
                             JwtRequestFilter jwtRequestFilter) {
        this.jwtAuthenticationEntryPoint = jwtAuthenticationEntryPoint;
        this.jwtUserDetailsService = jwtUserDetailsService;
        this.jwtRequestFilter = jwtRequestFilter;
    }

    /*any method annotated with @Autowired is a config method. It is called on bean instantiation*/
    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(jwtUserDetailsService).passwordEncoder(passwordEncoder());
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Override
    protected void configure(HttpSecurity httpSecurity) throws Exception {
        httpSecurity.csrf().disable()
                .authorizeRequests().antMatchers("/api/login", "/api/register").permitAll()
                .anyRequest().authenticated()
                .and()
                // make sure we use stateless session; session won't be used to
                // store user's state.
                .exceptionHandling().authenticationEntryPoint(jwtAuthenticationEntryPoint)
                .and()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);

        // Add a filter to validate the tokens with every request before UsernamePasswordAuthenticationFilter
        httpSecurity.addFilterBefore(jwtRequestFilter, UsernamePasswordAuthenticationFilter.class);
    }


    @Bean
    public WebMvcConfigurer corsConfigurer() {
        return new WebMvcConfigurer() {
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                registry.addMapping("/**").allowedOrigins("*").allowedMethods("*");
            }
        };
    }


    /*
     * Those OPTIONS request described below, are actually named pre-flight requests
     *
     * Web browsers for example chrome, sends OPTIONS http request to check
     * if they can properly use/connect to endpoints, before request that  we actually send,
     * they do this once, if they receive OK response , then they stop sending OPTIONS
     * request before our right request.
     *
     * Before this method was added JwtTokenFilter intercepted every request even
     * OPTION request, OPTION requests don't have jwt token since it is sent by browser
     * then JwtTokenFilter rejected those requests, and in browser console this poped out:
     * " Access to XMLHttpRequest at 'http://localhost:8080/api/drivers/'
     * from origin 'http://localhost:4200' has been blocked by CORS policy:
     * Response to preflight request doesn't pass access control check: It
     * does not have HTTP ok status."
     *
     * So this method causes that JwtRequestFilter doesnt intercept OPTIONS request
     * so that browser gets OK response from server on OPTION request, that means
     * this endpoint is OK for browser, that means CORS problem is solved. !
     *
     * Whole story of solving this bug:
     * 1. Moving Cors config from annotation based to bean based so that there is one config
     *    for every controller. ESSENTIAL: I SET ALLOWED CORS METHOD TO ALL SO THAT, SPRING
     *    ALLOWS pre-flight request aka OPTIONS
     * 2. Wrote this below method: to unlock every endpoint for pre-flight requests.
     *    so that when JwtRequestFilter reject pre flight token due to lack of token,
     *    it is ignored by spring, and spring still allows pre flight requests to
     *    succeed.     *
     */
    //todo try to handle this problem differently
    @Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring().antMatchers(HttpMethod.OPTIONS, "/**");
    }
}

