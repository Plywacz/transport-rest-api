package org.mplywacz.transitapi.dto;
/*
Author: BeGieU
Date: 08.10.2019
*/

import javax.validation.constraints.NotBlank;

//@JsonDeserialize(using = DriverDtoDeserializer.class)
public class DriverDto {
    @NotBlank(message = "first name cannot be empty")
    private String firstName;

    @NotBlank(message = "last name cannot be empty")
    private String lastName;

    public DriverDto() {
    }

    public DriverDto(String fName, String lName) {
        this.firstName = fName;
        this.lastName = lName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }
}