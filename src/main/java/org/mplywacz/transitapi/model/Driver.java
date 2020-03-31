package org.mplywacz.transitapi.model;
/*
Author: BeGieU
Date: 02.07.2019
*/

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import org.mplywacz.transitapi.json.LocalDateSerializer;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Entity
public class Driver extends BasicEntity implements Comparable<Driver> {

    /**
     * When driver is enrolled to the company
     * then we add him to DB, therefore
     * enrolledDate is set by service not sent with json
     */
    @JsonSerialize(using = LocalDateSerializer.class)
    //fixes bug, before jackson mapped this field as array ([26,11,2019])xd
    private LocalDate enrolledDate;

    private String firstName;

    private String lastName;

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

    @Override
    public int compareTo(Driver o) {
        return this.lastName.compareTo(o.lastName);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;
        Driver driver = (Driver) o;
        return
                Objects.equals(enrolledDate, driver.enrolledDate) &&
                Objects.equals(firstName, driver.firstName) &&
                Objects.equals(lastName, driver.lastName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(enrolledDate, firstName, lastName);
    }

    //getters and setters
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

    //i want driver json to have  transits sorted by date, that is why this method converts hashset -> treeset
    public Set<Transit> getTransits() {
        return this.transits;
    }

    public void setTransits(Set<Transit> transits) {
        this.transits = transits;
    }

    public LocalDate getEnrolledDate() {
        return enrolledDate;
    }
}
