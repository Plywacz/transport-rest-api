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

    @NotBlank(message = "userName  cannot be empty")
    private String userName;
    @NotBlank(message = "last name cannot be empty")
    private String password;

    private DriverDto() {

    }

    public static DriverDto buildDriverDto(JsonNode fName, JsonNode lName,
                                           JsonNode uName, JsonNode pWord) {
        var dto = new DriverDto();
        dto.setFirstName(fName);
        dto.setLastName(lName);
        dto.setUserName(uName);
        dto.setPassword(pWord);
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

    public String getUserName() {
        return userName;
    }

    public void setUserName(JsonNode userName) {
        this.userName = getNodeValue(userName, "you have not provided proper userName of driver");
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(JsonNode password) {
        this.password = getNodeValue(password, "you have not provided proper password of driver");
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