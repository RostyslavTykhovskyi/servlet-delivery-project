package com.mycompany.delivery.model.service;

import com.mycompany.delivery.model.dao.DaoFactory;
import com.mycompany.delivery.model.dao.UserDao;
import com.mycompany.delivery.model.entity.Order;
import com.mycompany.delivery.model.entity.User;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import java.util.List;

public class UserService {
    private static final Logger log = LogManager.getLogger(UserService.class);

    private final DaoFactory daoFactory = DaoFactory.getInstance();

    public void payForOrder(Order order) {
        log.info("User with id " + order.getUser().getId() + " pays for order with id = " + order.getId());
        try (UserDao userDao = daoFactory.createUserDao()) {
            userDao.payForOrder(order);
        }
    }

    public void topUpUserBalance(String username, int amount) {
        log.info("User with username + " + username + " top up his balance");
        try (UserDao userDao = daoFactory.createUserDao()) {
            userDao.topUpUserBalance(username, amount);
        }
    }

    public List<User> findPaginated(int page, int pageSize, String sortField, String sortDirection) {
        log.info("Finding paginated users");
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
        log.info("Saving user");
        try (UserDao userDao = daoFactory.createUserDao()) {
            userDao.save(user);
        }
    }

    public User findById(long id) {
        log.info("Finding user with id = " + id);
        try (UserDao userDao = daoFactory.createUserDao()) {
            return userDao.findById(id).orElseThrow(
                    () -> new RuntimeException("User with id " + id + " was not found")
            );
        }
    }

    public User findByUsername(String username) {
        log.info("Finding user with username = " + username);
        try (UserDao userDao = daoFactory.createUserDao()) {
            return userDao.findByUsername(username).orElseThrow(
                    () -> new RuntimeException("User with username \"" + username + "\" was not found")
            );
        }
    }

    public List<User> findAll() {
        log.info("Finding all users");
        try (UserDao userDao = daoFactory.createUserDao()) {
            return userDao.findAll();
        }
    }
}
