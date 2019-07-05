package org.mplywacz.demo.repositories;
/*
Author: BeGieU
Date: 05.07.2019
*/

import org.mplywacz.demo.model.Transit;
import org.springframework.data.repository.CrudRepository;

public interface TransitRepo extends CrudRepository<Transit,Long> {
}
