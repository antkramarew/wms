package com.demo.wms.repository;

import com.demo.wms.domain.UserRole;
import org.springframework.data.repository.CrudRepository;

/**
 * Created by toxa on 8/27/2016.
 */
public interface RoleRepository extends CrudRepository<UserRole, Long> {
}
