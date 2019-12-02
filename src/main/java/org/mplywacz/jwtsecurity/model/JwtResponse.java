package org.mplywacz.jwtsecurity.model;
/*
Author: BeGieU
Date: 26.11.2019
*/

public class JwtResponse   {
    private final String jwtToken;

    public JwtResponse(String jwtToken) {
        this.jwtToken = jwtToken;
    }

    public String getToken() {
        return this.jwtToken;
    }
}
