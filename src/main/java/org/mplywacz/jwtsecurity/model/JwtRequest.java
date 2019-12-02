package org.mplywacz.jwtsecurity.model;
/*
Author: BeGieU
Date: 26.11.2019
*/

import javax.validation.constraints.NotBlank;

public class JwtRequest {

    @NotBlank
    private final String username;
    @NotBlank
    private final String password;

    public JwtRequest(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public String getUsername() {
        return this.username;
    }

    public String getPassword() {
        return this.password;
    }

}
