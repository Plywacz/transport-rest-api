package org.mplywacz.transitapi.dto;
/*
Author: BeGieU
Date: 01.11.2019
*/

import java.util.List;

/*
 * Couldn't implement BasicDriverInfo, TotalInfo, MonthlyInfo as inner classes because it causes problems with JPQL query,
 * even public static inner classes included
 */
//todo update swagger doc on this
public class DriverReport {
    private final BasicDriverInfo basicInfo;

    private final TotalDriverInfo totalDriverInfo;

    private final List<MonthlyDriverInfo> monthlyDriverInfos;

    public DriverReport(BasicDriverInfo basicInfo, TotalDriverInfo totalDriverInfo, List<MonthlyDriverInfo> monthlyDriverInfos) {
        this.basicInfo = basicInfo;
        this.totalDriverInfo = totalDriverInfo;
        this.monthlyDriverInfos = monthlyDriverInfos;
    }

    public BasicDriverInfo getBasicInfo() {
        return basicInfo;
    }

    public TotalDriverInfo getTotalDriverInfo() {
        return totalDriverInfo;
    }

    public List<MonthlyDriverInfo> getMonthlyDriverInfos() {
        return monthlyDriverInfos;
    }
}
