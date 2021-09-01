package com.mycompany.delivery.model.dao.impl;

import com.mycompany.delivery.model.dao.RouteDao;
import com.mycompany.delivery.model.dao.mapper.OrderMapper;
import com.mycompany.delivery.model.dao.mapper.RoleMapper;
import com.mycompany.delivery.model.dao.mapper.RouteMapper;
import com.mycompany.delivery.model.dao.mapper.UserMapper;
import com.mycompany.delivery.model.entity.Order;
import com.mycompany.delivery.model.entity.Route;
import com.mycompany.delivery.model.entity.User;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class JDBCRouteDao implements RouteDao {
    private final Connection connection;

    public JDBCRouteDao(Connection connection) {
        this.connection = connection;
    }

    @Override
    public void save(Route route) {
        String query = "" +
                "INSERT INTO route(route_id, departure_point, arrival_point, length) " +
                "VALUES(nextval('route_id_seq'), ?, ?, ?)";

        try (PreparedStatement statement = connection.prepareStatement(query)) {

            statement.setString(1, route.getDeparturePoint());
            statement.setString(2, route.getArrivalPoint());
            statement.setInt(3, route.getLength());

            statement.executeUpdate();

        } catch (SQLException ex) {
            throw new RuntimeException(ex);
        }
    }

    @Override
    public Optional<Route> findById(Long id) {
        Optional<Route> route = Optional.empty();
        final String query = "SELECT * FROM route WHERE route_id = " + id;

        try (Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(query)) {

            RouteMapper routeMapper = new RouteMapper();

            if (resultSet.next()) {
                route = Optional.of(routeMapper.extractFromResultSet(resultSet));
            }
        } catch (SQLException ex) {
            throw new RuntimeException(ex);
        }

        return route;
    }

    @Override
    public List<Route> findAll() {
        List<Route> routes = new ArrayList<>();
        final String query = "SELECT * FROM route";

        try (Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(query)) {

            Route route;
            RouteMapper routeMapper = new RouteMapper();

            while (resultSet.next()) {
                route = routeMapper.extractFromResultSet(resultSet);
                routes.add(route);
            }
        } catch (SQLException ex) {
            throw new RuntimeException(ex);
        }

        return routes;
    }

    @Override
    public List<Route> findPaginated(int page, int pageSize, String sortField, String sortDirection) {
        List<Route> routes = new ArrayList<>();

        long start = (long) (page - 1) * pageSize;

        final String query = "" +
                "SELECT * FROM route " +
                "ORDER BY " + sortField + " " + sortDirection + " " +
                "LIMIT " + pageSize + " OFFSET " + start;

        try (Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(query)) {

            Route route;
            RouteMapper routeMapper = new RouteMapper();

            while (resultSet.next()) {
                route = routeMapper.extractFromResultSet(resultSet);
                routes.add(route);
            }
        } catch (SQLException ex) {
            throw new RuntimeException(ex);
        }

        return routes;
    }

    @Override
    public Long getNumberOfRows() {
        long count = 0;
        String query = "SELECT COUNT(route_id) count FROM route";

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
    public void update(Route route) {
        String query = "" +
                "UPDATE route " +
                "SET departure_point = ?, arrival_point = ?, length = ? " +
                "WHERE route_id = ?";

        try (PreparedStatement statement = connection.prepareStatement(query)) {

            statement.setString(1, route.getDeparturePoint());
            statement.setString(2, route.getArrivalPoint());
            statement.setInt(3, route.getLength());
            statement.setLong(4, route.getId());

            statement.executeUpdate();

        } catch (SQLException ex) {
            throw new RuntimeException(ex);
        }
    }

    @Override
    public void delete(Long id) {
        String query = "DELETE FROM route WHERE route_id = ?";

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
