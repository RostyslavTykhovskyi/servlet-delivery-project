package com.mycompany.delivery.model.service;

import com.mycompany.delivery.model.dao.DaoFactory;
import com.mycompany.delivery.model.dao.UserDao;
import com.mycompany.delivery.model.entity.Order;
import com.mycompany.delivery.model.entity.User;

import java.util.List;

public class UserService {
    private final DaoFactory daoFactory = DaoFactory.getInstance();

    public void payForOrder(Order order) {
        try (UserDao userDao = daoFactory.createUserDao()) {
            userDao.payForOrder(order);
        }
    }

    public List<User> findPaginated(int page, int pageSize, String sortField, String sortDirection) {
        try (UserDao userDao = daoFactory.createUserDao()) {
            return userDao.findPaginated(page, pageSize, sortField, sortDirection);
        }
    }

    public int getNumberOfPages(int pageSize) {
        try (UserDao userDao = daoFactory.createUserDao()) {
            return (int) Math.ceil((double) userDao.getNumberOfRows() / pageSize);
        }
    }

    public void saveUser(User user) {
        try (UserDao userDao = daoFactory.createUserDao()) {
            userDao.save(user);
        }
    }

    public User findById(long id) {
        try (UserDao userDao = daoFactory.createUserDao()) {
            return userDao.findById(id).orElseThrow(
                    () -> new RuntimeException("User with id " + id + " was not found")
            );
        }
    }

    public User findByUsername(String username) {
        try (UserDao userDao = daoFactory.createUserDao()) {
            return userDao.findByUsername(username).orElseThrow(
                    () -> new RuntimeException("User with username \"" + username + "\" was not found")
            );
        }
    }

    public List<User> findAll() {
        try (UserDao userDao = daoFactory.createUserDao()) {
            return userDao.findAll();
        }
    }

    public void updateUser(User user) {
        try (UserDao userDao = daoFactory.createUserDao()) {
            userDao.update(user);
        }
    }
}
