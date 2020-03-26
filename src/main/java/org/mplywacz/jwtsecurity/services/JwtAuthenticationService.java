package org.mplywacz.jwtsecurity.services;
/*
Author: BeGieU
Date: 26.11.2019
*/

import org.mplywacz.jwtsecurity.config.JwtTokenUtil;
import org.mplywacz.jwtsecurity.model.JwtResponse;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

@Service
public class JwtAuthenticationService implements AuthenticationService {
    private final AuthenticationManager authenticationManager;

    private final JwtTokenUtil jwtTokenUtil;

    private final JwtUserDetailsService userDetailsService;

    public JwtAuthenticationService(AuthenticationManager authenticationManager,
                                    JwtTokenUtil jwtTokenUtil,
                                    JwtUserDetailsService userDetailsService) {
        this.authenticationManager = authenticationManager;
        this.jwtTokenUtil = jwtTokenUtil;
        this.userDetailsService = userDetailsService;
    }

    @Override
    public JwtResponse authenticate(String uName, String passWd) throws Exception {
        authenticateCredentials(uName, passWd);

        final UserDetails userDetails = userDetailsService
                .loadUserByUsername(uName);

        final String tokenStr = jwtTokenUtil.generateToken(userDetails);
        return new JwtResponse(userDetails.getUsername(),
                tokenStr);
    }

    private void authenticateCredentials(String username, String password) throws Exception {
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
    }
}
