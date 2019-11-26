package org.mplywacz.transitapi.repositories;
/*
Author: BeGieU
Date: 05.07.2019
*/

import org.mplywacz.transitapi.dto.DailyInfo;
import org.mplywacz.transitapi.model.Transit;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.time.LocalDate;
import java.util.List;
import java.util.Set;

public interface TransitRepo extends CrudRepository<Transit, Long> {
    //SELECT * FROM transit t WHERE t.date >= '2019-09-01' AND t.date <= '2019-09-18';
    @Query(value = "SELECT * FROM transit t WHERE t.date >= ?1 AND t.date <= ?2",
            nativeQuery = true)
    Set<Transit> selectTransitsInDateRange(LocalDate date1, LocalDate date2);

    //https://stackoverflow.com/questions/36328063/how-to-return-a-custom-object-from-a-spring-data-jpa-group-by-query
    @Query(value = "SELECT " +
            "new org.mplywacz.transitapi.dto.DailyInfo(t.date, SUM(t.distance), AVG(t.distance), AVG(t.price)) " +
            "from Transit t " +
            "WHERE t.date >= ?1 AND t.date <= ?2 " +
            "GROUP BY t.date " +
            "ORDER BY t.date")
    List<DailyInfo> getMonthlyInfoFromDB(LocalDate date1, LocalDate date2);
}
