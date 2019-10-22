package org.mplywacz.demo.repositories;
/*
Author: BeGieU
Date: 14.10.2019
*/

import org.mplywacz.demo.model.AppUser;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AppUserRepo extends CrudRepository<AppUser, Long> {
    AppUser findByUsername(String uName);
}
