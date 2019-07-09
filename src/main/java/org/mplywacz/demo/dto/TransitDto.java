package org.mplywacz.demo.dto;
/*
Author: BeGieU
Date: 04.07.2019
*/

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import org.mplywacz.demo.json.TransitDtoDeserialization;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.sql.Date;

@JsonDeserialize(using = TransitDtoDeserialization.class)
public class TransitDto {
    @NotBlank(message = "source address must not be blank")
    private String sourceAddress;

    @NotBlank(message = "destination address must not be blank")
    private String destinationAddress;

    @Min(value = 0,message = "price must be greater than 0")
    @Max(value = 9999999,message = "price must be lesser than 9999999")
    private BigDecimal price;

    @NotNull(message = "you have to provide valid date")
    @DateTimeFormat(pattern = "yyyy-mm-dd") //todo migrate type of date because  @DateTimeFormat doesnt service current type
    private Date date;

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

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }
}
