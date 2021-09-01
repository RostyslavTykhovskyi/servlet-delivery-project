package com.mycompany.delivery.model.dao;

import com.mycompany.delivery.model.entity.Order;

import java.util.List;

public interface OrderDao extends GenericDao<Order, Long> {
    List<Order> findPaginatedByUserId(long userId, int page, int pageSize, String sortField, String sortDirection);
}
