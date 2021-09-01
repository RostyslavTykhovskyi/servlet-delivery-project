package com.mycompany.delivery.model.dao.impl;

import com.mycompany.delivery.model.dao.UserDao;
import com.mycompany.delivery.model.dao.mapper.RoleMapper;
import com.mycompany.delivery.model.dao.mapper.UserMapper;
import com.mycompany.delivery.model.entity.Order;
import com.mycompany.delivery.model.entity.Status;
import com.mycompany.delivery.model.entity.User;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class JDBCUserDao implements UserDao {
    private final Connection connection;

    public JDBCUserDao(Connection connection) {
        this.connection = connection;
    }

    @Override
    public void payForOrder(Order order) {
        String payForOrderQuery = "UPDATE \"user\" SET balance = balance - ? WHERE user_id = ?";
        String updateOrderStatusQuery = "UPDATE \"order\" SET status = ? WHERE order_id = ?";

        try (PreparedStatement payForOrderStatement = connection.prepareStatement(payForOrderQuery);
             PreparedStatement updateOrderStatusStatement = connection.prepareStatement(updateOrderStatusQuery)) {

            connection.setAutoCommit(false);

            payForOrderStatement.setInt(1, order.getCost());
            payForOrderStatement.setLong(2, order.getUser().getId());
            payForOrderStatement.executeUpdate();

            updateOrderStatusStatement.setString(1, Status.PAID.toString());
            updateOrderStatusStatement.setLong(2, order.getId());
            updateOrderStatusStatement.executeUpdate();

            connection.commit();

        } catch (SQLException tex) {
            try {
                connection.rollback();
                throw new RuntimeException(tex);
            } catch (SQLException rex) {
                throw new RuntimeException(rex);
            }
        }
    }

    @Override
    public void save(User user) {
        String query = "" +
                "INSERT INTO \"user\"(" +
                "user_id, " +
                "username, " +
                "email, " +
                "password, " +
                "balance, " +
                "account_non_expired, " +
                "account_non_locked, " +
                "credentials_non_expired, " +
                "enabled) " +
                "VALUES(nextval('user_id_seq'), ?, ?, ?, ?, ?, ?, ?, ?)";

        try (PreparedStatement statement = connection.prepareStatement(query)) {

            statement.setString(1, user.getUsername());
            statement.setString(2, user.getEmail());
            statement.setString(3, user.getPassword());
            statement.setInt(4, user.getBalance());
            statement.setBoolean(5, user.isAccountNonExpired());
            statement.setBoolean(6, user.isAccountNonLocked());
            statement.setBoolean(7, user.isCredentialsNonExpired());
            statement.setBoolean(8, user.isEnabled());

            statement.executeUpdate();

        } catch (SQLException ex) {
            throw new RuntimeException(ex);
        }
    }

    @Override
    public Optional<User> findById(Long id) {
        Optional<User> user = Optional.empty();
        final String query = "" +
                "SELECT * FROM \"user\" u " +
                "LEFT JOIN user_authorities ua ON u.user_id = ua.users_user_id " +
                "LEFT JOIN role r ON r.role_id = ua.authorities_role_id " +
                "WHERE u.user_id = " + id;

        try (Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(query)) {

            UserMapper userMapper = new UserMapper();
            RoleMapper roleMapper = new RoleMapper();

            if (resultSet.next()) {
                user = Optional.of(userMapper.extractFromResultSet(resultSet));
                user.get().setRole(roleMapper.extractFromResultSet(resultSet));
            }
        } catch (SQLException ex) {
            throw new RuntimeException(ex);
        }

        return user;
    }

    public Optional<User> findByUsername(String username) {
        Optional<User> user = Optional.empty();
        final String query = "" +
                "SELECT * FROM \"user\" u " +
                "LEFT JOIN user_authorities ua ON u.user_id = ua.users_user_id " +
                "LEFT JOIN role r ON r.role_id = ua.authorities_role_id " +
                "WHERE u.username = '" + username + "'";

        try (Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(query)) {

            UserMapper userMapper = new UserMapper();
            RoleMapper roleMapper = new RoleMapper();

            if (resultSet.next()) {
                user = Optional.of(userMapper.extractFromResultSet(resultSet));
                user.get().setRole(roleMapper.extractFromResultSet(resultSet));
            }
        } catch (SQLException ex) {
            throw new RuntimeException(ex);
        }

        return user;
    }

    @Override
    public List<User> findAll() {
        List<User> users = new ArrayList<>();
        final String query = "" +
                "SELECT * FROM \"user\" u " +
                "LEFT JOIN user_authorities ua ON u.user_id = ua.users_user_id " +
                "LEFT JOIN role r ON r.role_id = ua.authorities_role_id";

        try (Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(query)) {

            User user;
            UserMapper userMapper = new UserMapper();
            RoleMapper roleMapper = new RoleMapper();

            while (resultSet.next()) {
                user = userMapper.extractFromResultSet(resultSet);
                user.setRole(roleMapper.extractFromResultSet(resultSet));

                users.add(user);
            }
        } catch (SQLException ex) {
            throw new RuntimeException(ex);
        }

        return users;
    }

    @Override
    public List<User> findPaginated(int page, int pageSize, String sortField, String sortDirection) {
        List<User> users = new ArrayList<>();

        long start = (long) (page - 1) * pageSize;

        final String query = "" +
                "SELECT * FROM \"user\" u " +
                "LEFT JOIN user_authorities ua ON u.user_id = ua.users_user_id " +
                "LEFT JOIN role r ON r.role_id = ua.authorities_role_id " +
                "ORDER BY u." + sortField + " " + sortDirection + " " +
                "LIMIT " + pageSize + " OFFSET " + start;

        System.out.println(query);

        try (Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(query)) {

            User user;
            UserMapper userMapper = new UserMapper();
            RoleMapper roleMapper = new RoleMapper();

            while (resultSet.next()) {
                user = userMapper.extractFromResultSet(resultSet);
                user.setRole(roleMapper.extractFromResultSet(resultSet));

                users.add(user);
            }
        } catch (SQLException ex) {
            throw new RuntimeException(ex);
        }

        return users;
    }

    @Override
    public Long getNumberOfRows() {
        long count = 0;
        String query = "SELECT COUNT(user_id) count FROM \"user\"";

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
    public void update(User user) {
        String query = "" +
                "UPDATE \"user\" " +
                "SET username = ?, " +
                "email = ?, " +
                "password = ?, " +
                "balance = ?, " +
                "account_non_expired = ?, " +
                "account_non_locked = ?, " +
                "credentials_non_expired = ?, " +
                "enabled = ? " +
                "WHERE user_id = ?";

        try (PreparedStatement statement = connection.prepareStatement(query)) {

            statement.setString(1, user.getUsername());
            statement.setString(2, user.getEmail());
            statement.setString(3, user.getPassword());
            statement.setInt(4, user.getBalance());
            statement.setBoolean(5, user.isAccountNonExpired());
            statement.setBoolean(6, user.isAccountNonLocked());
            statement.setBoolean(7, user.isCredentialsNonExpired());
            statement.setBoolean(8, user.isEnabled());
            statement.setLong(9, user.getId());

            statement.executeUpdate();

        } catch (SQLException ex) {
            throw new RuntimeException(ex);
        }
    }

    @Override
    public void delete(Long id) {
        String query = "DELETE FROM \"user\" WHERE user_id = ?";

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
