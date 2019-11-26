package org.mplywacz.transitapi.dto;
/*
Author: BeGieU
Date: 01.11.2019
*/

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.math.BigDecimal;
import java.util.LinkedList;
import java.util.List;

@ApiModel(description = "Summary of certain driver")
public class DriverReport {
    @ApiModelProperty(
            notes = "first name of person who this report is about",
            required = true,
            position = 0,
            example = "Lebron")
    private final String firstName;

    @ApiModelProperty(
            notes = "last name of person who this report is about",
            required = true,
            position = 1,
            example = "James")
    private final String lastName;

    @ApiModelProperty(
            notes = "number of km's driven by this driver",
            required = true,
            position = 2,
            example = "1234")
    private final BigDecimal totalDistance;

    @ApiModelProperty(
            notes = "longest transit driven by this driver in km's",
            required = true,
            position = 3,
            example = "8987751")
    private final BigDecimal longestTransit;

    @ApiModelProperty(
            notes = "most expensive transit driven by this driver",
            required = true,
            position = 4,
            example = "123451")
    private final BigDecimal mostExpensiveTransit;

    @ApiModelProperty(
            notes = "contains monthly summary transits driven by this driver",
            required = true,
            dataType = "List<MonthlyInfo>",
            position = 5)
    private final List<MonthlyInfo> monthlyInfos = new LinkedList<>();

    @ApiModel(description = "Helper class to sum up transits took in particular month bycertain driver")
    private class MonthlyInfo {
        @ApiModelProperty(
                notes = "date of this month",
                required = true,
                example = "2019-12",
                position = 0)
        private final String yearMonthDate;

        @ApiModelProperty(
                notes = "distance driven by this driver per particular month",
                required = true,
                example = "2142",
                position = 1)
        private final BigDecimal distancePerMonth;

        @ApiModelProperty(
                notes = "longest transit driven by this driver per particular month",
                required = true,
                example = "212",
                position = 2)
        private final BigDecimal longestTransitPerMonth;

        @ApiModelProperty(
                notes = "most expensive transit driven by this driver per particular month",
                required = true,
                example = "421521",
                position = 3)
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
