package org.mplywacz.jwtsecurity.controllers;
/*
Author: BeGieU
Date: 26.11.2019
*/

import org.mplywacz.jwtsecurity.model.JwtRequest;
import org.mplywacz.jwtsecurity.model.JwtResponse;
import org.mplywacz.jwtsecurity.model.User;
import org.mplywacz.jwtsecurity.model.UserDto;
import org.mplywacz.jwtsecurity.services.AuthenticationService;
import org.mplywacz.jwtsecurity.services.JwtUserDetailsService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/")
public class JwtAuthenticationController {

    private final JwtUserDetailsService userDetailsService;

    private final AuthenticationService authenticationService;

    public JwtAuthenticationController(
            JwtUserDetailsService userDetailsService,
            AuthenticationService authenticationService) {

        this.userDetailsService = userDetailsService;
        this.authenticationService = authenticationService;
    }

    @PostMapping(value = "/login")
    public JwtResponse createAuthenticationToken(@RequestBody @Valid JwtRequest authenticationRequest) throws Exception {
        return authenticationService.authenticate(
                authenticationRequest.getUsername(),
                authenticationRequest.getPassword());

    }

    @PostMapping(value = "/register")
    public User saveUser(@RequestBody @Valid UserDto user) throws Exception {
        return userDetailsService.save(user);
    }


}
