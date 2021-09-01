package com.mycompany.delivery.model.dao;

import com.mycompany.delivery.model.dao.impl.JDBCDaoFactory;

public abstract class DaoFactory {
    private static volatile DaoFactory daoFactory;

    public abstract UserDao createUserDao();
    public abstract RoleDao createRoleDao();
    public abstract RouteDao createRouteDao();
    public abstract OrderDao createOrderDao();

    public static DaoFactory getInstance() {
        if (daoFactory == null) {
            synchronized (DaoFactory.class) {
                if (daoFactory == null) {
                    daoFactory = new JDBCDaoFactory();
                }
            }
        }
        return daoFactory;
    }
}
