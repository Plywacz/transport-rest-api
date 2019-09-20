package org.mplywacz.demo.model;
/*
Author: BeGieU
Date: 19.09.2019
*/

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import org.mplywacz.demo.json.DailyInfoSerializer;

import java.math.BigDecimal;
import java.time.LocalDate;

@JsonSerialize(using = DailyInfoSerializer.class)
public class DailyInfo {
    private LocalDate date;
    private BigDecimal totalDistance;
    private double avgDistance;
    private double avgPrice;

    public DailyInfo() {
    }


    //constructor is based on what is returned from JPA query in TransitRepo,
    public DailyInfo(LocalDate date, BigDecimal totalDistance,
                     double avgDistance, double avgPrice) {
        this.date = date;
        this.totalDistance = totalDistance;
        this.avgDistance = avgDistance;
        this.avgPrice = avgPrice;
    }


    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public BigDecimal getTotalDistance() {
        return totalDistance;
    }

    public void setTotalDistance(BigDecimal totalDistance) {
        this.totalDistance = totalDistance;
    }

    public double getAvgDistance() {
        return avgDistance;
    }

    public void setAvgDistance(double avgDistance) {
        this.avgDistance = avgDistance;
    }

    public double getAvgPrice() {
        return avgPrice;
    }

    public void setAvgPrice(double avgPrice) {
        this.avgPrice = avgPrice;
    }
}
