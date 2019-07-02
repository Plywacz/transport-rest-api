package org.mplywacz.demo.model;
/*
Author: BeGieU
Date: 02.07.2019
*/

import javax.persistence.Entity;
import java.math.BigDecimal;
import java.sql.Date;

@Entity
public class Transit extends BasicEntity {

    private String sourceAddress;
    private String destinationAddress;
    private BigDecimal price;
    private Date date;

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

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Driver getDriver() {
        return driver;
    }

    public void setDriver(Driver driver) {
        this.driver = driver;
    }
}
