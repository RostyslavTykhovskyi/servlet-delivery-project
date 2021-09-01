package com.mycompany.delivery.model.dao;

import com.mycompany.delivery.model.entity.Role;

import java.util.Optional;

public interface RoleDao extends GenericDao<Role, Long> {
    Optional<Role> findByName(String name);
}
