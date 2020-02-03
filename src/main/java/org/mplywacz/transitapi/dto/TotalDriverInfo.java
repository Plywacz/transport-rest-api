package org.mplywacz.transitapi.dto;
/*
Author: BeGieU
Date: 03.02.2020
*/

import java.math.BigDecimal;

public class TotalDriverInfo {
    private BigDecimal totalDistance;

    private BigDecimal longestTransit;

    private BigDecimal mostExpensiveTransit;

    public TotalDriverInfo(BigDecimal totalDistance, BigDecimal longestTransit, BigDecimal mostExpensiveTransit) {
        this.totalDistance = totalDistance;
        this.longestTransit = longestTransit;
        this.mostExpensiveTransit = mostExpensiveTransit;
    }

    public BigDecimal getTotalDistance() {
        return totalDistance;
    }

    public BigDecimal getLongestTransit() {
        return longestTransit;
    }

    public BigDecimal getMostExpensiveTransit() {
        return mostExpensiveTransit;
    }
}
