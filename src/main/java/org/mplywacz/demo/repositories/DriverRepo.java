package org.mplywacz.demo.repositories;
/*
Author: BeGieU
Date: 08.10.2019
*/

import org.mplywacz.demo.model.Driver;
import org.springframework.data.repository.CrudRepository;

public interface DriverRepo extends CrudRepository<Driver, Long> {
}
