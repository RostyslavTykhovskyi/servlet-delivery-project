package com.mycompany.delivery.model.dao;

import com.mycompany.delivery.model.entity.Order;
import com.mycompany.delivery.model.entity.User;

import java.util.Optional;

public interface UserDao extends GenericDao<User, Long> {
    Optional<User> findByUsername(String username);
    void payForOrder(Order order);
    void topUpUserBalance(String username, int amount);
}
