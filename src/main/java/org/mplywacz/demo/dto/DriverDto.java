package org.mplywacz.demo.dto;
/*
Author: BeGieU
Date: 08.10.2019
*/

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import org.mplywacz.demo.json.DriverDtoDeserializer;

import javax.validation.constraints.NotBlank;

@JsonDeserialize(using = DriverDtoDeserializer.class)
public class DriverDto {
    @NotBlank(message = "first name cannot be empty")
    private String firstName;
    @NotBlank(message = "last name cannot be empty")
    private String lastName;

    public DriverDto(String fName, String lName) {
        this.firstName = fName;
        this.lastName = lName;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }
}