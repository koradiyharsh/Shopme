package com.shopme.admin.users;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.shopme.common.entities.Role;


@Repository
public interface RoleRepositories extends CrudRepository<Role, Integer> {

}
