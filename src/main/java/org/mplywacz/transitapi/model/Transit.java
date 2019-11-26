package org.mplywacz.transitapi.model;
/*
Author: BeGieU
Date: 02.07.2019
*/

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.mplywacz.transitapi.json.TransitDtoSerializer;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import java.math.BigDecimal;
import java.time.LocalDate;

@ApiModel(description = "Details of driver")
@Entity
@JsonSerialize(using = TransitDtoSerializer.class)
public class Transit extends BasicEntity {

    @ApiModelProperty(
            notes = "source address",
            required = true,
            position = 1,
            example = "ul. Zakręt 8, Poznań")
    private String sourceAddress;

    @ApiModelProperty(
            notes = "destination address",
            required = true,
            position = 2,
            example = "ul. Krakowska 18, Tarnów")
    private String destinationAddress;

    @ApiModelProperty(
            notes = "price of transit",
            required = true,
            position = 3,
            example = "6261.12")
    private BigDecimal price;

    @ApiModelProperty(
            notes = "date of this transit",
            required = true,
            position = 4)
    @Column(
            name = "date",
            columnDefinition = "DATE")
    private LocalDate date;
    //needs to be calculated based source and destiny address
    @ApiModelProperty(
            notes = "distance of this transit in km",
            required = true,
            position = 5,
            example = "52512.12")
    private BigDecimal distance;

    @ApiModelProperty(
            name = "driver_id",
            notes = "id of driver who owns this transit",
            required = true,
            position = 6,
            example = "0")
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
