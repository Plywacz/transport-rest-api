package org.mplywacz.demo.repositories;
/*
Author: BeGieU
Date: 05.07.2019
*/

import org.mplywacz.demo.model.Transit;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.sql.Date;
import java.util.Set;

public interface TransitRepo extends CrudRepository<Transit, Long> {

    @Query(value = "SELECT * FROM transit t WHERE t.date >= ?1 AND t.date<=?2",
            nativeQuery = true)
    Set<Transit> selectTransitsInDateRange(Date date1, Date date2);
}
