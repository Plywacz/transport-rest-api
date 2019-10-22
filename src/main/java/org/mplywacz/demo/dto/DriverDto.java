package org.mplywacz.demo.dto;
/*
Author: BeGieU
Date: 08.10.2019
*/

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import org.mplywacz.demo.json.DriverDtoDeserializer;

import javax.validation.constraints.NotBlank;

@JsonDeserialize(using = DriverDtoDeserializer.class)
public class DriverDto {
    @NotBlank(message = "first name cannot be empty")
    private String firstName;
    @NotBlank(message = "last name cannot be empty")
    private String lastName;


    private DriverDto() {

    }

    public static DriverDto buildDriverDto(JsonNode fName, JsonNode lName) {
        var dto = new DriverDto();
        dto.setFirstName(fName);
        dto.setLastName(lName);
        return dto;
    }

    public String getFirstName() {
        return firstName;
    }

    private void setFirstName(JsonNode firstName) {
        this.firstName = getNodeValue(firstName, "you have not provided first Name of driver");

    }

    public String getLastName() {
        return lastName;
    }

    private void setLastName(JsonNode lastName) {
        this.lastName = getNodeValue(lastName, "you have not provided last Name of driver");
    }


    private String getNodeValue(JsonNode node, String errorMsg) {
        if (node != null) {
            return node.asText();
        }
        else {
            throw new IllegalArgumentException(errorMsg);
        }
    }
}