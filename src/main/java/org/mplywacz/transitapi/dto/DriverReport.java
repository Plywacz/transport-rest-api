package org.mplywacz.transitapi.dto;
/*
Author: BeGieU
Date: 01.11.2019
*/

import java.math.BigDecimal;
import java.util.LinkedList;
import java.util.List;

public class DriverReport {

    private final String firstName;


    private final String lastName;


    private final BigDecimal totalDistance;


    private final BigDecimal longestTransit;


    private final BigDecimal mostExpensiveTransit;


    private final List<MonthlyInfo> monthlyInfos = new LinkedList<>();

    private class MonthlyInfo {

        private final String yearMonthDate;


        private final BigDecimal distancePerMonth;


        private final BigDecimal longestTransitPerMonth;


        private final BigDecimal mostExpensiveTransitPerMonth;

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

    public DriverReport(String firstName, String lastName,
                        BigDecimal totalDistance, BigDecimal longestTransit,
                        BigDecimal mostExpensiveTransit) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.totalDistance = totalDistance;
        this.longestTransit = longestTransit;
        this.mostExpensiveTransit = mostExpensiveTransit;
    }

    public void addMonthlyInfo(String yearMonthDate, BigDecimal distancePerMonth,
                               BigDecimal longestTransitPerMonth, BigDecimal mostExpensiveTransitPerMonth) {
        var monthlyInfo = this.new MonthlyInfo(
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
