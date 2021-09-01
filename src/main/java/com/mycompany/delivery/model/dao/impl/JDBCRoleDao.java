package com.mycompany.delivery.model.dao.impl;

import com.mycompany.delivery.model.dao.RoleDao;
import com.mycompany.delivery.model.dao.mapper.RoleMapper;
import com.mycompany.delivery.model.entity.Role;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class JDBCRoleDao implements RoleDao {
    private final Connection connection;

    public JDBCRoleDao(Connection connection) {
        this.connection = connection;
    }

    @Override
    public void save(Role role) {
        String query = "INSERT INTO role(role_id, name) VALUES(nextval('role_id_seq'), ?)";

        try (PreparedStatement statement = connection.prepareStatement(query)) {

            statement.setString(1, role.getName());

            statement.executeUpdate();

        } catch (SQLException ex) {
            throw new RuntimeException(ex);
        }
    }

    @Override
    public Optional<Role> findById(Long id) {
        Optional<Role> role = Optional.empty();
        final String query = "SELECT * FROM role WHERE role_id = " + id;

        try (Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(query)) {

            RoleMapper roleMapper = new RoleMapper();

            if (resultSet.next()) {
                role = Optional.of(roleMapper.extractFromResultSet(resultSet));
            }
        } catch (SQLException ex) {
            throw new RuntimeException(ex);
        }

        return role;
    }

    @Override
    public Optional<Role> findByName(String name) {
        Optional<Role> role = Optional.empty();
        final String query = "SELECT * FROM role WHERE name = '" + name + "'";

        try (Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(query)) {

            RoleMapper roleMapper = new RoleMapper();

            if (resultSet.next()) {
                role = Optional.of(roleMapper.extractFromResultSet(resultSet));
            }
        } catch (SQLException ex) {
            throw new RuntimeException(ex);
        }

        return role;
    }

    @Override
    public List<Role> findAll() {
        List<Role> roles = new ArrayList<>();
        final String query = "SELECT * FROM role";

        try (Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(query)) {

            Role role;
            RoleMapper roleMapper = new RoleMapper();

            while (resultSet.next()) {
                role = roleMapper.extractFromResultSet(resultSet);
                roles.add(role);
            }
        } catch (SQLException ex) {
            throw new RuntimeException(ex);
        }

        return roles;
    }

    @Override
    public List<Role> findPaginated(int page, int pageSize, String sortField, String sortDirection) {
        List<Role> roles = new ArrayList<>();

        long start = (long) (page - 1) * pageSize;

        final String query = "" +
                "SELECT * FROM role " +
                "ORDER BY " + sortField + " " + sortDirection + " " +
                "LIMIT " + pageSize + " OFFSET " + start;

        try (Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(query)) {

            Role role;
            RoleMapper roleMapper = new RoleMapper();

            while (resultSet.next()) {
                role = roleMapper.extractFromResultSet(resultSet);
                roles.add(role);
            }
        } catch (SQLException ex) {
            throw new RuntimeException(ex);
        }

        return roles;
    }

    @Override
    public Long getNumberOfRows() {
        long count = 0;
        String query = "SELECT COUNT(role_id) count FROM role";

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
    public void update(Role role) {
        String query = "UPDATE role SET name = ? WHERE role_id = ?";

        try (PreparedStatement statement = connection.prepareStatement(query)) {

            statement.setString(1, role.getName());
            statement.setLong(2, role.getId());

            statement.executeUpdate();

        } catch (SQLException ex) {
            throw new RuntimeException(ex);
        }
    }

    @Override
    public void delete(Long id) {
        String query = "DELETE FROM role WHERE role_id = ?";

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
