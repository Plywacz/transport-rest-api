package org.mplywacz.transitapi.model;
/*
Author: BeGieU
Date: 02.07.2019
*/

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@ApiModel(description = "All details about the driver")
@Entity
public class Driver extends BasicEntity {

    /**
     * When driver is enrolled to the company
     * then we add him to DB, therefore
     * enrolledDate is set by service not sent with json
     */
    @ApiModelProperty(
            notes = "enrollment date",
            required = true,
            position = 1)
    private LocalDate enrolledDate;

    @ApiModelProperty(
            notes = "first name of employee",
            required = true,
            position = 2,
            example = "Jan")
    private String firstName;
    @ApiModelProperty(
            notes = "last name of employee",
            required = true,
            position = 3,
            example = "Doe")
    private String lastName;

    @ApiModelProperty(
            notes = "transits realised by this driver",
            required = true,
            dataType = "Set<Transit>",
            position = 4)
    @OneToMany(
            cascade = CascadeType.ALL,
            mappedBy = "driver",
            fetch = FetchType.LAZY)
    private Set<Transit> transits = new HashSet<>();

    /**
     * For hibernate usage
     */
    public Driver() {
    }

    public Driver(String firstName, String lastName, LocalDate enrolledDate) {
        super();
        this.enrolledDate = enrolledDate;
        this.firstName = firstName;
        this.lastName = lastName;
    }

    public void addTransit(Transit transit) {
        transits.add(transit);
    }

    public void setEnrolledDate(LocalDate enrolledDate) {
        this.enrolledDate = enrolledDate;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public Set<Transit> getTransits() {
        return transits;
    }

    public void setTransits(Set<Transit> transits) {
        this.transits = transits;
    }

    public LocalDate getEnrolledDate() {
        return enrolledDate;
    }
}
