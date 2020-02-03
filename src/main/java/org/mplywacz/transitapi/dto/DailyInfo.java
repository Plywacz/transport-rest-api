package org.mplywacz.transitapi.dto;
/*
Author: BeGieU
Date: 19.09.2019
*/

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import org.mplywacz.transitapi.json.DailyInfoSerializer;

import java.math.BigDecimal;
import java.time.LocalDate;

@JsonSerialize(using = DailyInfoSerializer.class)
public class DailyInfo {
    private final LocalDate date;
    private final BigDecimal totalDistance;
    private final double avgDistance;
    private final double avgPrice;

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

    public BigDecimal getTotalDistance() {
        return totalDistance;
    }

    public double getAvgDistance() {
        return avgDistance;
    }

    public double getAvgPrice() {
        return avgPrice;
    }

}
