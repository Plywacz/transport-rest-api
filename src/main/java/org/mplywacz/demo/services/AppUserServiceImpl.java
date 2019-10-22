package org.mplywacz.demo.services;
/*
Author: BeGieU
Date: 14.10.2019
*/

import org.mplywacz.demo.model.AppUser;
import org.mplywacz.demo.repositories.AppUserRepo;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AppUserServiceImpl implements AppUserService {
    private final AppUserRepo appUserRepo;
    private final PasswordEncoder passwordEncoder;

    public AppUserServiceImpl(AppUserRepo appUserRepo, PasswordEncoder passwordEncoder) {
        this.appUserRepo = appUserRepo;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        var user = appUserRepo.findByUsername(username);
        if (user == null) {
            throw new UsernameNotFoundException(username);
        }
        return user;
    }

    @Override public AppUser addUser(AppUser appUser) {
        appUser.setPassword(passwordEncoder.encode(appUser.getPassword()));
        return appUserRepo.save(appUser);
    }

    @Override public void deleteUser(Long id) {
        //todo implement this

    }

}
