package org.mplywacz.demo.model;
/*
Author: BeGieU
Date: 02.07.2019
*/

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import java.time.LocalDate;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

@Entity
public class Driver extends BasicEntity implements UserDetails {

    public static final String ROLE = "USER";
    private String firstName;
    private String lastName;

    private String username;
    private String password;

    /**
     * When driver is enrolled to the company
     * then we add him to DB, therefore
     * enrolDate is set by service not sent with json
     */
    private LocalDate enrolDate;

    /**
     * For hibernate usage
     */
    public Driver() {
    }

    public Driver(String firstName, String lastName, String username, String password,
                  LocalDate enrolDate) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.username = username;
        this.password = password;
        this.enrolDate = enrolDate;
    }

    @OneToMany(cascade = CascadeType.ALL,
            mappedBy = "driver",
            fetch = FetchType.LAZY)
    private Set<Transit> transits = new HashSet<>();

    public void addTransit(Transit transit) {
        transits.add(transit);
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Collections.singleton(new SimpleGrantedAuthority(ROLE));
    }

    @Override
    public String getPassword() {
        return this.password;
    }

    @Override
    public String getUsername() {
        return this.username;
    }

    // app doesnt need such advanced features so returned values are fixed
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    public Set<Transit> getTransits() {
        return transits;
    }

    public void setTransits(Set<Transit> transits) {
        this.transits = transits;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public LocalDate getEnrolDate() {
        return enrolDate;
    }
}
