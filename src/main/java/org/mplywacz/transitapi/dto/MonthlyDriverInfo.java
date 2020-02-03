package org.mplywacz.transitapi.dto;
/*
Author: BeGieU
Date: 03.02.2020
*/

import java.math.BigDecimal;

public class MonthlyDriverInfo {
    private String yearMonthDate;
    private BigDecimal distancePerMonth;
    private BigDecimal longestTransitPerMonth;
    private BigDecimal mostExpensiveTransitPerMonth;

    public MonthlyDriverInfo(String yearMonthDate, BigDecimal distancePerMonth,
                             BigDecimal longestTransitPerMonth, BigDecimal mostExpensiveTransitPerMonth) {
        this.yearMonthDate = yearMonthDate;
        this.distancePerMonth = distancePerMonth;
        this.longestTransitPerMonth = longestTransitPerMonth;
        this.mostExpensiveTransitPerMonth = mostExpensiveTransitPerMonth;
    }

    public String getYearMonthDate() {
        return yearMonthDate;
    }

    public BigDecimal getDistancePerMonth() {
        return distancePerMonth;
    }

    public BigDecimal getLongestTransitPerMonth() {
        return longestTransitPerMonth;
    }

    public BigDecimal getMostExpensiveTransitPerMonth() {
        return mostExpensiveTransitPerMonth;
    }
}
