package org.mplywacz.demo.dto;
/*
Author: BeGieU
Date: 04.07.2019
*/

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import io.swagger.annotations.ApiModelProperty;
import org.mplywacz.demo.json.TransitDtoDeserializer;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.time.LocalDate;

@JsonDeserialize(using = TransitDtoDeserializer.class)
public class TransitDto {

    @ApiModelProperty(
            notes = "id of the driver this transport belongs to",
            required = true,
            position = 0,
            example = "0")
    @NotNull(message = "driver id cannot be null")
    private Long driverId;

    @ApiModelProperty(
            notes = "address where this transport starts",
            required = true,
            position = 1,
            example = "ul. Zakręt 8, Poznań")
    @NotBlank(message = "source address must not be blank")
    private String sourceAddress;

    @ApiModelProperty(
            notes = "address where this transport ends",
            required = true,
            position = 2,
            example = "ul. Nowodąbrowska 18, Tarnów")
    @NotBlank(message = "destination address must not be blank")
    private String destinationAddress;

    @ApiModelProperty(
            notes = "price of this transport",
            required = true,
            position = 3,
            example = "12342")
    @Min(value = 0, message = "price must be greater than 0")
    @Max(value = 9999999, message = "price must be lesser than 9999999")
    private BigDecimal price;

    @ApiModelProperty(
            notes = "date of this transport",
            required = true,
            position = 4,
            example = "2016-12-11")
    @NotNull(message = "you have to provide valid date")
    private LocalDate date;

    public TransitDto(Long driverId,
                      String sourceAddress,
                      String destinationAddress,
                      BigDecimal price,
                      LocalDate date) {
        this.driverId = driverId;
        this.sourceAddress = sourceAddress;
        this.destinationAddress = destinationAddress;
        this.price = price;
        this.date = date;
    }

    public Long getDriverId() {
        return driverId;
    }

    public String getSourceAddress() {
        return sourceAddress;
    }

    public String getDestinationAddress() {
        return destinationAddress;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public LocalDate getDate() {
        return date;
    }


}
