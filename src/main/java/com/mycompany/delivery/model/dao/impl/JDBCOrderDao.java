package com.mycompany.delivery.model.dao.impl;

import com.mycompany.delivery.model.dao.OrderDao;
import com.mycompany.delivery.model.dao.mapper.OrderMapper;
import com.mycompany.delivery.model.dao.mapper.RouteMapper;
import com.mycompany.delivery.model.dao.mapper.UserMapper;
import com.mycompany.delivery.model.entity.Order;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class JDBCOrderDao implements OrderDao {
    private final Connection connection;

    public JDBCOrderDao(Connection connection) {
        this.connection = connection;
    }

    @Override
    public void save(Order order) {
        String query = "" +
                "INSERT INTO \"order\"(" +
                "order_id, " +
                "cost, " +
                "weight, " +
                "length, " +
                "width, " +
                "height, " +
                "address, " +
                "status, " +
                "delivery_date, " +
                "created_on, " +
                "user_id, " +
                "route_id) " +
                "VALUES(nextval('order_id_seq'), ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

        try (PreparedStatement statement = connection.prepareStatement(query)) {

            statement.setInt(1, order.getCost());
            statement.setDouble(2, order.getWeight());
            statement.setDouble(3, order.getLength());
            statement.setDouble(4, order.getWidth());
            statement.setDouble(5, order.getHeight());
            statement.setString(6, order.getAddress());
            statement.setString(7, order.getStatus().toString());
            statement.setDate(8, Date.valueOf(order.getDeliveryDate()));
            statement.setTimestamp(9, Timestamp.valueOf(order.getCreatedOn()));
            statement.setLong(10, order.getUser().getId());
            statement.setLong(11, order.getRoute().getId());

            statement.executeUpdate();

        } catch (SQLException ex) {
            throw new RuntimeException(ex);
        }
    }

    @Override
    public Optional<Order> findById(Long id) {
        Optional<Order> order = Optional.empty();
        final String query = "" +
                "SELECT * FROM \"order\" " +
                "LEFT JOIN \"user\" USING (user_id) " +
                "LEFT JOIN route USING (route_id) " +
                "WHERE order_id = " + id;

        try (Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(query)) {

            OrderMapper orderMapper = new OrderMapper();
            UserMapper userMapper = new UserMapper();
            RouteMapper routeMapper = new RouteMapper();

            if (resultSet.next()) {
                order = Optional.of(orderMapper.extractFromResultSet(resultSet));
                order.get().setUser(userMapper.extractFromResultSet(resultSet));
                order.get().setRoute(routeMapper.extractFromResultSet(resultSet));
            }
        } catch (SQLException ex) {
            throw new RuntimeException(ex);
        }

        return order;
    }

    @Override
    public List<Order> findAll() {
        List<Order> orders = new ArrayList<>();
        final String query = "" +
                "SELECT * FROM \"order\" " +
                "LEFT JOIN \"user\" USING (user_id) " +
                "LEFT JOIN route USING (route_id)";

        try (Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(query)) {

            Order order;
            OrderMapper orderMapper = new OrderMapper();
            UserMapper userMapper = new UserMapper();
            RouteMapper routeMapper = new RouteMapper();

            while (resultSet.next()) {
                order = orderMapper.extractFromResultSet(resultSet);
                order.setUser(userMapper.extractFromResultSet(resultSet));
                order.setRoute(routeMapper.extractFromResultSet(resultSet));

                orders.add(order);
            }
        } catch (SQLException ex) {
            throw new RuntimeException(ex);
        }

        return orders;
    }

    @Override
    public List<Order> findPaginated(int page, int pageSize, String sortField, String sortDirection) {
        List<Order> orders = new ArrayList<>();

        long start = (long) (page - 1) * pageSize;

        final String query = "" +
                "SELECT * FROM \"order\" " +
                "LEFT JOIN \"user\" USING (user_id) " +
                "LEFT JOIN route USING (route_id) " +
                "ORDER BY " + sortField + " " + sortDirection + " " +
                "LIMIT " + pageSize + " OFFSET " + start;

        try (Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(query)) {

            Order order;
            OrderMapper orderMapper = new OrderMapper();
            UserMapper userMapper = new UserMapper();
            RouteMapper routeMapper = new RouteMapper();

            while (resultSet.next()) {
                order = orderMapper.extractFromResultSet(resultSet);
                order.setUser(userMapper.extractFromResultSet(resultSet));
                order.setRoute(routeMapper.extractFromResultSet(resultSet));

                orders.add(order);
            }
        } catch (SQLException ex) {
            throw new RuntimeException(ex);
        }

        return orders;
    }

    @Override
    public List<Order> findPaginatedByUserId(long userId, int page, int pageSize, String sortField, String sortDirection) {
        List<Order> orders = new ArrayList<>();

        long start = (long) (page - 1) * pageSize;

        final String query = "" +
                "SELECT * FROM \"order\" " +
                "LEFT JOIN \"user\" USING (user_id) " +
                "LEFT JOIN route USING (route_id) " +
                "WHERE user_id = " + userId + " " +
                "ORDER BY " + sortField + " " + sortDirection + " " +
                "LIMIT " + pageSize + " OFFSET " + start;

        try (Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(query)) {

            Order order;
            OrderMapper orderMapper = new OrderMapper();
            UserMapper userMapper = new UserMapper();
            RouteMapper routeMapper = new RouteMapper();

            while (resultSet.next()) {
                order = orderMapper.extractFromResultSet(resultSet);
                order.setUser(userMapper.extractFromResultSet(resultSet));
                order.setRoute(routeMapper.extractFromResultSet(resultSet));

                orders.add(order);
            }
        } catch (SQLException ex) {
            throw new RuntimeException(ex);
        }

        return orders;
    }

    @Override
    public Long getNumberOfRows() {
        long count = 0;
        String query = "SELECT COUNT(order_id) count FROM \"order\"";

        try (Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(query)) {

            if (resultSet.next()) {
                count = resultSet.getLong("count");
            }

            return count;
        } catch (SQLException ex) {
            throw new RuntimeException(ex);
        }
    }

    @Override
    public void update(Order order) {
        String query = "" +
                "UPDATE \"order\" " +
                "SET cost = ?, " +
                "weight = ?, " +
                "length = ?, " +
                "width = ?, " +
                "height = ?, " +
                "address = ?, " +
                "status = ?, " +
                "delivery_date = ?, " +
                "created_on = ? " +
                "WHERE order_id = ?";

        try (PreparedStatement statement = connection.prepareStatement(query)) {

            statement.setInt(1, order.getCost());
            statement.setDouble(2, order.getWeight());
            statement.setDouble(3, order.getLength());
            statement.setDouble(4, order.getWidth());
            statement.setDouble(5, order.getHeight());
            statement.setString(6, order.getAddress());
            statement.setString(7, order.getStatus().toString());
            statement.setDate(8, Date.valueOf(order.getDeliveryDate()));
            statement.setTimestamp(9, Timestamp.valueOf(order.getCreatedOn()));
            statement.setLong(10, order.getId());

            statement.executeUpdate();

        } catch (SQLException ex) {
            throw new RuntimeException(ex);
        }
    }

    @Override
    public void delete(Long id) {
        String query = "DELETE FROM \"order\" WHERE order_id = ?";

        try (PreparedStatement statement = connection.prepareStatement(query)) {

            statement.setLong(1, id);

            statement.executeUpdate();

        } catch (SQLException ex) {
            throw new RuntimeException(ex);
        }
    }

    @Override
    public void close() {
        try {
            connection.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
