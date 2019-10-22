package org.mplywacz.demo.services;
/*
Author: BeGieU
Date: 14.10.2019
*/

import org.mplywacz.demo.model.AppUser;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface AppUserService extends UserDetailsService {
    AppUser addUser(AppUser appUser);

    void deleteUser(Long id);
}
