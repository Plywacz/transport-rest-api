package org.mplywacz.demo.repositories;
/*
Author: BeGieU
Date: 05.07.2019
*/

import org.mplywacz.demo.model.Transit;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.time.LocalDate;
import java.util.Set;

public interface TransitRepo extends CrudRepository<Transit, Long> {
//SELECT * FROM transit t WHERE t.date >= '2019-09-01' AND t.date <= '2019-09-18';
    @Query(value = "SELECT * FROM transit t WHERE t.date >= ?1 AND t.date <= ?2",
            nativeQuery = true)
    Set<Transit> selectTransitsInDateRange(LocalDate date1, LocalDate date2);
}
