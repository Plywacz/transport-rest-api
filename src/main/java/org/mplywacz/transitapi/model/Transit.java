package org.mplywacz.transitapi.model;
/*
Author: BeGieU
Date: 02.07.2019
*/

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import org.mplywacz.transitapi.json.TransitSerializer;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Objects;

@Entity
@JsonSerialize(using = TransitSerializer.class)
public class Transit extends BasicEntity implements Comparable<LocalDate>{

    private String sourceAddress;

    private String destinationAddress;

    private BigDecimal price;

    @Column(
            name = "date",
            columnDefinition = "DATE")
    private LocalDate date;
    //needs to be calculated based source and destiny address
    private BigDecimal distance;

    @ManyToOne
    @JoinColumn(name = "driver_id")
    private Driver driver;

    public Transit() {
    }

    @Override
    public int compareTo(LocalDate o) {
        return this.date.compareTo(o);
    }

    @Override public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;
        if (!super.equals(o))
            return false;
        Transit transit = (Transit) o;
        return
                Objects.equals(sourceAddress, transit.sourceAddress) &&
                Objects.equals(destinationAddress, transit.destinationAddress) &&
                Objects.equals(price, transit.price) &&
                Objects.equals(date, transit.date) &&
                Objects.equals(distance, transit.distance)&&
                Objects.equals(this.driver,transit.driver);
    }

    @Override public int hashCode() {
        return Objects.hash( sourceAddress, destinationAddress, price, date, distance,driver);
    }

    //getters and setters
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
