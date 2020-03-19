package org.astashonok.onlinestorebackend.daoImpl;

import org.astashonok.onlinestorebackend.dao.RoleDAO;
import org.astashonok.onlinestorebackend.dto.Role;
import org.astashonok.onlinestorebackend.exceptions.basicexception.OnlineStoreLogicalException;
import org.astashonok.onlinestorebackend.util.pool.Pool;
import org.astashonok.onlinestorebackend.util.pool.PoolWithDataSource;
import org.astashonok.onlinestorebackend.util.pool.Pools;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class RoleDAOImpl implements RoleDAO {

    private Pool pool;

    public RoleDAOImpl(Pool pool) {
        this.pool = pool;
    }

    public RoleDAOImpl() {
        pool = Pools.newPool(PoolWithDataSource.class);
    }

    public void setPool(Pool pool) {
        this.pool = pool;
    }

    private Connection getConnection() throws SQLException {
        return pool.getConnection();
    }

    @Override
    public List<Role> getAll() {
        String sql = "SELECT id, name, active FROM roles";
        List<Role> roles = new ArrayList<>();
        Role role;
        try (Connection connection = getConnection(); PreparedStatement preparedStatement = connection.prepareStatement(sql)
             ; ResultSet resultSet = preparedStatement.executeQuery()) {
            while (resultSet.next()) {
                role = new Role();
                role.setId(resultSet.getLong("id"));
                role.setName(resultSet.getString("name"));
                role.setActive(resultSet.getBoolean("active"));
                roles.add(role);
            }
        } catch (SQLException | OnlineStoreLogicalException e) {
            roles = null;
            e.printStackTrace();
        }
        return roles;
    }

    @Override
    public List<Role> getAllActive() {
        String sql = "SELECT id, name, active FROM roles WHERE active = 1";
        List<Role> roles = new ArrayList<>();
        Role role;
        try (Connection connection = getConnection(); PreparedStatement preparedStatement = connection.prepareStatement(sql)
             ; ResultSet resultSet = preparedStatement.executeQuery()) {
            while (resultSet.next()) {
                role = new Role();
                role.setId(resultSet.getLong("id"));
                role.setName(resultSet.getString("name"));
                role.setActive(resultSet.getBoolean("active"));
                roles.add(role);
            }
        } catch (SQLException | OnlineStoreLogicalException e) {
            roles = null;
            e.printStackTrace();
        }
        return roles;
    }

    @Override
    public Role getByName(String name) {
        String sql = "SELECT id, name, active FROM roles WHERE name = '" + name + "'";
        Role role = null;
        try (Connection connection = getConnection(); PreparedStatement preparedStatement = connection.prepareStatement(sql)
             ; ResultSet resultSet = preparedStatement.executeQuery()) {
            if (resultSet.next()) {
                role = new Role();
                role.setId(resultSet.getLong("id"));
                role.setName(resultSet.getString("name"));
                role.setActive(resultSet.getBoolean("active"));
            }
        } catch (SQLException | OnlineStoreLogicalException e) {
            role = null;
            e.printStackTrace();
        }
        return role;
    }

    @Override
    public long add(Role entity) {
        String sql = "INSERT INTO roles (name, active) VALUES (?, ?)";
        long id = 0;
        ResultSet generatedKeys = null;
        try (Connection connection = getConnection()
             ; PreparedStatement preparedStatement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            preparedStatement.setString(1, entity.getName());
            preparedStatement.setBoolean(2, entity.isActive());
            if (preparedStatement.executeUpdate() == 0) {
                throw new SQLException("Creating address is failed, no rows is affected! ");
            }
            generatedKeys = preparedStatement.getGeneratedKeys();
            if (generatedKeys.next()) {
                id = generatedKeys.getLong(1);
                entity.setId(id);
            } else {
                throw new SQLException("Creating address is failed, no id is obtained! ");
            }
        } catch (SQLException | OnlineStoreLogicalException e) {
            e.printStackTrace();
        } finally {
            if (generatedKeys != null){
                try {
                    generatedKeys.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
        return id;
    }

    @Override
    public Role getById(long id) {
        String sql = "SELECT id, name, active FROM roles WHERE id = " + id;
        Role role = null;
        try (Connection connection = getConnection(); PreparedStatement preparedStatement = connection.prepareStatement(sql)
             ; ResultSet resultSet = preparedStatement.executeQuery()) {
            if (resultSet.next()) {
                role = new Role();
                role.setId(resultSet.getLong("id"));
                role.setName(resultSet.getString("name"));
                role.setActive(resultSet.getBoolean("active"));
            }
        } catch (SQLException | OnlineStoreLogicalException e) {
            role = null;
            e.printStackTrace();
        }
        return role;
    }

    @Override
    public boolean edit(Role entity) {
        String sql = "UPDATE roles SET name = ?,  active = ? WHERE id = ?";
        try (Connection connection = getConnection(); PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setLong(3, entity.getId());
            preparedStatement.setString(1, entity.getName());
            preparedStatement.setBoolean(2, entity.isActive());
            if (preparedStatement.executeUpdate() == 0) {
                throw new SQLException("Creating role is failed, no rows is affected! ");
            }
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean remove(Role entity) {
        entity.setActive(false);
        return edit(entity);
    }


    public static Role getRoleById(long id, Connection connection, PreparedStatement preparedStatement
            , ResultSet resultSet) throws SQLException, OnlineStoreLogicalException {
        String sql = "SELECT id, name, active FROM roles WHERE id = " + id;
        Role role = null;
        preparedStatement = connection.prepareStatement(sql);
        resultSet = preparedStatement.executeQuery();
        if (resultSet.next()) {
            role = new Role();
            role.setId(resultSet.getLong("id"));
            role.setName(resultSet.getString("name"));
            role.setActive(resultSet.getBoolean("active"));
        }
        return role;
    }
}
