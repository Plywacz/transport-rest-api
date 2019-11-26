package org.mplywacz.jwtsecurity.repo;
/*
Author: BeGieU
Date: 26.11.2019
*/

import org.mplywacz.jwtsecurity.model.UserDao;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepo extends CrudRepository<UserDao, Long> {
    UserDao findByUsername(String name);
}
