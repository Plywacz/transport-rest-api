package org.mplywacz.transitapi.dto;
/*
Author: BeGieU
Date: 03.02.2020
*/

public class BasicDriverInfo {
    private final Long id;
    private final String firstName;
    private final String lastName;

    public BasicDriverInfo(Long id, String firstName, String lastName) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
    }

    public Long getId() {
        return id;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }
}
