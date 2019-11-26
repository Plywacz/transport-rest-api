package org.mplywacz.jwtsecurity.services;
/*
Author: BeGieU
Date: 26.11.2019
*/

import org.mplywacz.jwtsecurity.model.JwtResponse;

public interface AuthenticationService {
    JwtResponse authenticate(String uName, String passWd) throws Exception;
}
