package org.mplywacz.jwtsecurity.services;
/*
Author: BeGieU
Date: 26.11.2019
*/

import org.mplywacz.jwtsecurity.model.User;
import org.mplywacz.jwtsecurity.model.UserDto;
import org.mplywacz.jwtsecurity.repo.UserRepo;
import org.mplywacz.transitapi.exceptions.EntityAlreadyExistException;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class JwtUserDetailsService implements UserDetailsService {
    private final PasswordEncoder encoder;

    private final UserRepo userRepo;

    public JwtUserDetailsService(@Lazy PasswordEncoder encoder, UserRepo userRepo) {
        this.encoder = encoder;
        this.userRepo = userRepo;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepo.findByUsername(username);
        if (user == null) {
            throw new UsernameNotFoundException("User not found with username: " + username);
        }
        return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(),
                new ArrayList<>());
    }

    public User save(UserDto userDto) {
        String username = userDto.getUsername();
        if (userRepo.findByUsername(username) != null) {
            throw new EntityAlreadyExistException("username is not available");
        }

        User newUser = new User();
        newUser.setUsername(username);
        newUser.setPassword(encoder.encode(userDto.getPassword()));
        return userRepo.save(newUser);
    }
}
