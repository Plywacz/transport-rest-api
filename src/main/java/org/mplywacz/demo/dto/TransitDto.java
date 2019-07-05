package org.mplywacz.demo.dto;
/*
Author: BeGieU
Date: 04.07.2019
*/

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import java.math.BigDecimal;
import java.time.LocalDate;

public class TransitDto {
    @NotBlank
    private String sourceAddress;

    @NotBlank
    private String destinationAddress;

    @Min(0)
    @Max(9999999)
    private BigDecimal price;

    @NotBlank
    private LocalDate date;

    public TransitDto() {
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
}
