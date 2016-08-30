package com.demo.wms.repository;

import com.demo.wms.domain.User;
import org.springframework.data.repository.CrudRepository;

/**
 * Created by toxa on 8/21/2016.
 */
public interface UserRepository extends CrudRepository<User, Long> {

    User findUserByLogin(String login);
}
