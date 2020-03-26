package org.mplywacz.jwtsecurity.model;
/*
Author: BeGieU
Date: 26.11.2019
*/

public class JwtResponse {
    private final String username;
    private final String jwtToken;

    public JwtResponse(String username, String jwtToken) {
        this.username = username;
        this.jwtToken = jwtToken;
    }

    public String getToken() {
        return this.jwtToken;
    }

    public String getUsername() {
        return this.username;
    }
}
