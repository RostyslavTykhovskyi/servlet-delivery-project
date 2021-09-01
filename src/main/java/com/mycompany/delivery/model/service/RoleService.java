package com.mycompany.delivery.model.service;

import com.mycompany.delivery.model.dao.DaoFactory;
import com.mycompany.delivery.model.dao.RoleDao;
import com.mycompany.delivery.model.entity.Role;

public class RoleService {
    private final DaoFactory daoFactory = DaoFactory.getInstance();

    public void saveRole(Role role) {
        try (RoleDao roleDao = daoFactory.createRoleDao()) {
            roleDao.save(role);
        }
    }

    public Role findByName(String name) {
        try (RoleDao roleDao = daoFactory.createRoleDao()) {
            return roleDao.findByName(name).orElseThrow(
                    () -> new RuntimeException("Role with name \"" + name + "\" was not found")
            );
        }
    }
}
