package org.mplywacz.demo.model;
/*
Author: BeGieU
Date: 02.07.2019
*/

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Entity
public class Driver extends BasicEntity {

    private String firstName;
    private String lastName;

    /**
     *  When driver is enrolled to the company
     *  then we add him to DB, therefore
     *  enrolDate is set by service not sent with json
     */
    private LocalDate enrolDate;

    /**
     * For hibernate use
     */
    public Driver() {
    }

    public Driver(String firstName, String lastName, LocalDate enrolDate) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.enrolDate = enrolDate;
    }

    @OneToMany(cascade = CascadeType.ALL,
            mappedBy = "driver",
            fetch = FetchType.LAZY)
    private Set<Transit> transits = new HashSet<>();

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
