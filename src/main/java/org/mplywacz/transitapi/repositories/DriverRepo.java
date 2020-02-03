package org.mplywacz.transitapi.repositories;
/*
Author: BeGieU
Date: 08.10.2019
*/

import org.mplywacz.transitapi.dto.BasicDriverInfo;
import org.mplywacz.transitapi.dto.MonthlyDriverInfo;
import org.mplywacz.transitapi.dto.TotalDriverInfo;
import org.mplywacz.transitapi.model.Driver;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;


public interface DriverRepo extends CrudRepository<Driver, Long> {
    Driver findDriverByFirstNameAndLastName(String firstName, String lastName);

    @Query(value =
            "SELECT " +
                    "new org.mplywacz.transitapi.dto.MonthlyDriverInfo(function('DATE_FORMAT',t.date,'%Y-%m') , SUM(t.distance), MAX(t.distance),MAX(t.price)) " +
                    "FROM Transit t " +
                    "WHERE t.driver.id=?1 " +
                    "GROUP BY  function('YEAR',t.date), function('MONTH',t.date)")
    List<MonthlyDriverInfo> getMonthlyDriverInfo(Long driverId);

    @Query(value =
            "SELECT " +
                    "new org.mplywacz.transitapi.dto.TotalDriverInfo(SUM(t.distance),MAX(t.distance),MAX(t.price)) " +
                    "FROM Transit t " +
                    "INNER join Driver d ON t.driver.id = d.id " +
                    "WHERE t.driver.id=?1")
    TotalDriverInfo getTotalDriverInfo(Long driverId);

    @Query(value =
            "SELECT " +
                    "new org.mplywacz.transitapi.dto.BasicDriverInfo(  d.id,d.firstName,d.lastName )" +
                    "FROM Driver d " +
                    "WHERE d.id=?1")
    BasicDriverInfo getBasicDriverInfo(Long id);
}
