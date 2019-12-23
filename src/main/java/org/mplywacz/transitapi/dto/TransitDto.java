package org.mplywacz.transitapi.dto;
/*
Author: BeGieU
Date: 04.07.2019
*/

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.time.LocalDate;

//@JsonDeserialize(using = TransitDtoDeserializer.class)
public class TransitDto {
    @NotNull(message = "driver id cannot be null")
    private Long driverId;

    @NotBlank(message = "source address must not be blank")
    private String sourceAddress;

    @NotBlank(message = "destination address must not be blank")
    private String destinationAddress;

    @Min(value = 0, message = "price must be greater than 0")
    @Max(value = 9999999, message = "price must be lesser than 9999999")
    private BigDecimal price;

    @NotNull(message = "you have to provide valid date")
    private LocalDate date;

    public TransitDto() {
    }

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

    public void setDriverId(Long driverId) {
        this.driverId = driverId;
    }

    public void setSourceAddress(String sourceAddress) {
        this.sourceAddress = sourceAddress;
    }

    public void setDestinationAddress(String destinationAddress) {
        this.destinationAddress = destinationAddress;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public void setDate(LocalDate date) {
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
