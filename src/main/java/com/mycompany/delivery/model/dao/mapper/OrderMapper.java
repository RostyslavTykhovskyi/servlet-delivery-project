package com.mycompany.delivery.model.dao.mapper;

import com.mycompany.delivery.model.entity.Order;
import com.mycompany.delivery.model.entity.Status;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Map;

public class OrderMapper implements ObjectMapper<Order> {
    @Override
    public Order extractFromResultSet(ResultSet rs) throws SQLException {
        Order order = new Order();

        order.setId(rs.getInt("order_id"));
        order.setCost(rs.getInt("cost"));
        order.setWeight(rs.getDouble("weight"));
        order.setLength(rs.getDouble("length"));
        order.setWidth(rs.getDouble("width"));
        order.setHeight(rs.getDouble("height"));
        order.setAddress(rs.getString("address"));
        order.setDeliveryDate(rs.getDate("delivery_date").toLocalDate());
        order.setCreatedOn(rs.getTimestamp("created_on").toLocalDateTime());
        order.setStatus(Status.valueOf(rs.getString("status")));

        return order;
    }

    @Override
    public Order makeUnique(Map<Long, Order> cache, Order order) {
        cache.putIfAbsent(order.getId(), order);
        return cache.get(order.getId());
    }
}
