package org.mplywacz.jwtsecurity.model;
/*
Author: BeGieU
Date: 26.11.2019
*/

import javax.validation.constraints.NotBlank;

public class UserDto {
    @NotBlank
    private final String username;
    @NotBlank
    private final String password;

    public UserDto(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }
}
