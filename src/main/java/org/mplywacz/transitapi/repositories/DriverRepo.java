package org.mplywacz.transitapi.repositories;
/*
Author: BeGieU
Date: 08.10.2019
*/

import org.mplywacz.transitapi.model.Driver;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;


public interface DriverRepo extends CrudRepository<Driver, Long> {

    Driver findDriverByFirstNameAndLastName(String firstName,String lastName);

    @Query(value =
            "SELECT t.id,d.first_name ,d.last_name ,SUM(t.distance),MAX(t.distance),MAX(t.price) " +
                    "FROM transit t " +
                    "INNER join driver d ON t.driver_id = d.id " +
                    "WHERE t.driver_id=?1",
            nativeQuery = true)
    List<Object[]> getTotalReportForDriver(Long id);

    @Query(value =
            "SELECT " +
                    "DATE_FORMAT(t.date,'%Y-%m'), SUM(t.distance), MAX(t.distance),MAX(t.price) " +
                    "FROM transit t " +
                    "WHERE t.driver_id=?1 " +
                    "GROUP BY  YEAR(t.date), MONTH(t.date)DESC",
            nativeQuery = true)
    List<Object[]> getReportPerMonthForDriver(Long id);

}
