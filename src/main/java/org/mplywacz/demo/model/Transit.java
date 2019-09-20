package org.mplywacz.demo.model;
/*
Author: BeGieU
Date: 02.07.2019
*/

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
public class Transit extends BasicEntity {

    private String sourceAddress;
    private String destinationAddress;
    private BigDecimal price;
    @Column(name = "date", columnDefinition = "DATE")
    private LocalDate date;
    //needs to be calculated based source and destiny address
    private BigDecimal distance;

    @ManyToOne
    @JoinColumn(name = "driver_id")
    private Driver driver;

    public Transit() {
    }

    public String getSourceAddress() {
        return sourceAddress;
    }

    public void setSourceAddress(String sourceAddress) {
        this.sourceAddress = sourceAddress;
    }

    public String getDestinationAddress() {
        return destinationAddress;
    }

    public void setDestinationAddress(String destinationAddress) {
        this.destinationAddress = destinationAddress;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public Driver getDriver() {
        return driver;
    }

    public void setDriver(Driver driver) {
        this.driver = driver;
    }

    public BigDecimal getDistance() {
        return distance;
    }

    public void setDistance(BigDecimal distance) {
        this.distance = distance;
    }

}
