package org.mplywacz.jwtsecurity.controllers;
/*
Author: BeGieU
Date: 26.11.2019
*/

import org.mplywacz.jwtsecurity.model.JwtRequest;
import org.mplywacz.jwtsecurity.model.UserDto;
import org.mplywacz.jwtsecurity.services.AuthenticationService;
import org.mplywacz.jwtsecurity.services.JwtUserDetailsService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/users")
@CrossOrigin
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
    public ResponseEntity<?> createAuthenticationToken(@RequestBody @Valid JwtRequest authenticationRequest) throws Exception {

        return ResponseEntity.ok(
                authenticationService.authenticate(
                        authenticationRequest.getUsername(),
                        authenticationRequest.getPassword()
                )
        );
    }

    @PostMapping(value = "/register")
    public ResponseEntity<?> saveUser(@RequestBody @Valid UserDto user) throws Exception {
        return ResponseEntity.ok(userDetailsService.save(user));
    }


}
