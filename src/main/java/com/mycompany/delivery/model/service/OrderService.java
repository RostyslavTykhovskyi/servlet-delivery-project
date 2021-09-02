package com.mycompany.delivery.model.service;

import com.mycompany.delivery.model.dao.DaoFactory;
import com.mycompany.delivery.model.dao.OrderDao;
import com.mycompany.delivery.model.entity.Order;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import java.util.List;
import java.util.stream.Collectors;

public class OrderService {
    private static final Logger log = LogManager.getLogger(OrderService.class);

    private final DaoFactory daoFactory = DaoFactory.getInstance();

    public int calculateCost(Order order) {
        log.info("Calculating cost");
        return (int) (order.getRoute().getLength() / 10 + order.getLength() + order.getWidth() + order.getHeight() + order.getWeight());
    }

    public List<Order> findPaginatedByUserId(long userId, int page, int pageSize, String sortField, String sortDirection) {
        log.info("Finding paginated orders with user id = " + userId);
        try (OrderDao orderDao = daoFactory.createOrderDao()) {
            return orderDao.findPaginatedByUserId(userId, page, pageSize, sortField, sortDirection);
        }
    }

    public List<Order> findPaginated(int page, int pageSize, String sortField, String sortDirection) {
        log.info("Finding paginated orders");
        try (OrderDao orderDao = daoFactory.createOrderDao()) {
            return orderDao.findPaginated(page, pageSize, sortField, sortDirection);
        }
    }

    public int getNumberOfPages(int pageSize) {
        try (OrderDao orderDao = daoFactory.createOrderDao()) {
            return (int) Math.ceil((double) orderDao.getNumberOfRows() / pageSize);
        }
    }

    public void saveOrder(Order order) {
        log.info("Saving order");
        try (OrderDao orderDao = daoFactory.createOrderDao()) {
            orderDao.save(order);
        }
    }

    public Order findById(Long id) {
        log.info("Finding order with id = " + id);
        try (OrderDao orderDao = daoFactory.createOrderDao()) {
            return orderDao.findById(id).orElseThrow(() -> new RuntimeException("Order with id " + id + " was not found"));
        }
    }

    public List<Order> findAll() {
        log.info("Finding all orders");
        try (OrderDao orderDao = daoFactory.createOrderDao()) {
            return orderDao.findAll();
        }
    }

    public List<Order> findAllByUserId(long userId) {
        log.info("Finding all orders with user id = " + userId);
        try (OrderDao orderDao = daoFactory.createOrderDao()) {
            return orderDao.findAll().stream().filter((o) -> o.getUser().getId() == userId).collect(Collectors.toList());
        }
    }

    public void updateOrder(Order order) {
        log.info("Updating order with id = " + order.getId());
        try (OrderDao orderDao = daoFactory.createOrderDao()) {
            orderDao.update(order);
        }
    }
}
