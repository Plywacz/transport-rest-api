package org.mplywacz.transitapi.dto;
/*
Author: BeGieU
Date: 01.11.2019
*/

import java.math.BigDecimal;
import java.util.LinkedList;
import java.util.List;

public class DriverReport {
    private class MonthlyInfo {
        private String yearMonthDate;
        private BigDecimal distancePerMonth;
        private BigDecimal longestTransitPerMonth;
        private BigDecimal mostExpensiveTransitPerMonth;

        private MonthlyInfo(String yearMonthDate, BigDecimal distancePerMonth,
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

    private class TotalInfo {
        private BigDecimal totalDistance;

        private BigDecimal longestTransit;

        private BigDecimal mostExpensiveTransit;

        public TotalInfo() {
        }

        public TotalInfo(BigDecimal totalDistance, BigDecimal longestTransit, BigDecimal mostExpensiveTransit) {
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

    private final String firstName;

    private final String lastName;

    private TotalInfo totalInfo;

    private final List<MonthlyInfo> monthlyInfos = new LinkedList<>();

    public DriverReport(String firstName, String lastName) {
        this.firstName = firstName;
        this.lastName = lastName;
    }

    public void addMonthlyInfo(String yearMonthDate, BigDecimal distancePerMonth,
                               BigDecimal longestTransitPerMonth, BigDecimal mostExpensiveTransitPerMonth) {
        var monthlyInfo = new MonthlyInfo(
                yearMonthDate, distancePerMonth,
                longestTransitPerMonth, mostExpensiveTransitPerMonth);
        monthlyInfos.add(monthlyInfo);
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public List<MonthlyInfo> getMonthlyInfos() {
        return monthlyInfos;
    }

    public void setTotalInfo(BigDecimal totalDistance,
                             BigDecimal longestTransit,
                             BigDecimal mostExpensiveTransit) {
        this.totalInfo = new TotalInfo(totalDistance,
                longestTransit,
                mostExpensiveTransit);
    }

    public TotalInfo getTotalInfo() {
        return totalInfo;
    }
}
