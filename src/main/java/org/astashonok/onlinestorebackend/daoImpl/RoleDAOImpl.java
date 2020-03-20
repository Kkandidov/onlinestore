package org.astashonok.onlinestorebackend.daoImpl;

import org.astashonok.onlinestorebackend.dao.RoleDAO;
import org.astashonok.onlinestorebackend.dto.Role;
import org.astashonok.onlinestorebackend.exceptions.basicexception.BackendException;
import org.astashonok.onlinestorebackend.exceptions.basicexception.BackendLogicalException;
import org.astashonok.onlinestorebackend.exceptions.technicalexception.DatabaseException;
import org.astashonok.onlinestorebackend.util.ClassName;
import org.astashonok.onlinestorebackend.util.pool.Pool;
import org.astashonok.onlinestorebackend.util.pool.PoolWithDataSource;
import org.astashonok.onlinestorebackend.util.pool.Pools;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class RoleDAOImpl implements RoleDAO {

    private Pool pool;
    private static final Logger logger = LoggerFactory.getLogger(ClassName.getCurrentClassName());

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
    public List<Role> getAll() throws BackendException {
        logger.info("Invoking of getAll() method");
        return get("SELECT id, name, active FROM roles");
    }

    @Override
    public List<Role> getAllActive() throws BackendException {
        logger.info("Invoking of getAllActive() method");
        return get("SELECT id, name, active FROM roles WHERE active = 1");
    }

    private List<Role> get(String sql) throws BackendException {
        logger.info("Invoking of get(String sql) method: sql = {}", sql);
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
            logger.info("The roles were fetched from database: {}", roles);
        } catch (BackendLogicalException e) {
            logger.error("No roles were fetched from database", e);
            throw e;
        } catch (SQLException e) {
            logger.error("SQLException", e);
            throw new DatabaseException("SQLException", e);
        }
        return roles;
    }

    @Override
    public Role getByName(String name) throws BackendException {
        logger.info("Invoking of getByName(String name) method: name = {}", name);
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
            logger.info("The role was fetched from database: {}", role);
        } catch (BackendLogicalException e) {
            logger.error("No role was fetched from database", e);
            throw e;
        } catch (SQLException e) {
            logger.error("SQLException", e);
            throw new DatabaseException("SQLException", e);
        }
        return role;
    }

    @Override
    public long add(Role entity) throws BackendException {
        logger.info("Invoking of add(Role entity) method: {}", entity);
        String sql = "INSERT INTO roles (name, active) VALUES (?, ?)";
        long id = -1;
        ResultSet generatedKeys = null;
        try (Connection connection = getConnection()
             ; PreparedStatement preparedStatement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            preparedStatement.setString(1, entity.getName());
            preparedStatement.setBoolean(2, entity.isActive());
            if (preparedStatement.executeUpdate() == 0) {
                logger.error("Creating the role is failed, no rows is affected");
                throw new DatabaseException("Creating the role is failed, no rows is affected");
            }
            generatedKeys = preparedStatement.getGeneratedKeys();
            if (generatedKeys.next()) {
                id = generatedKeys.getLong(1);
                entity.setId(id);
            } else {
                logger.error("The role key isn't fetched from database");
                throw new DatabaseException("The role key isn't fetched from database");
            }
        } catch (BackendLogicalException e) {
            logger.error("The role wasn't added", e);
            throw e;
        } catch (SQLException e) {
            logger.error("SQLException", e);
            throw new DatabaseException("SQLException", e);
        } finally {
            if (generatedKeys != null) {
                try {
                    generatedKeys.close();
                } catch (SQLException e) {
                    logger.error("All the resources weren't closed", e);
                }
            }
        }
        return id;
    }

    @Override
    public Role getById(long id) throws BackendException {
        logger.info("Invoking of getById(long id), id = {}", id);
        Role role;
        try (Connection connection = getConnection()) {
            role = getRoleById(id, connection);
            logger.info("The role was fetched from database: {}", role);
        } catch (BackendLogicalException e) {
            logger.error("The role wasn't fetched from database", e);
            throw e;
        } catch (SQLException e) {
            logger.error("SQLException", e);
            throw new DatabaseException("SQLException", e);
        }
        return role;
    }

    @Override
    public boolean edit(Role entity) throws BackendException {
        logger.info("Invoking of edit(Role entity): {}", entity);
        String sql = "UPDATE roles SET name = ?,  active = ? WHERE id = ?";
        try (Connection connection = getConnection(); PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setLong(3, entity.getId());
            preparedStatement.setString(1, entity.getName());
            preparedStatement.setBoolean(2, entity.isActive());
            if (preparedStatement.executeUpdate() == 0) {
                logger.error("Updating the role is failed, no rows is affected");
                throw new DatabaseException("Updating the role is failed, no rows is affected");
            }
            logger.info("The role was updated");
            return true;
        } catch (SQLException e) {
            logger.error("SQLException", e);
            throw new DatabaseException("SQLException", e);
        }
    }

    @Override
    public boolean remove(Role entity) throws BackendException {
        logger.info("Invoking of remove(Role entity): {}", entity);
        entity.setActive(false);
        logger.info("Setting the active field to false");
        return edit(entity);
    }


    static Role getRoleById(long id, Connection connection) throws SQLException, BackendLogicalException {
        String sql = "SELECT id, name, active FROM roles WHERE id = " + id;
        Role role = null;
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)
             ; ResultSet resultSet = preparedStatement.executeQuery()) {
            if (resultSet.next()) {
                role = new Role();
                role.setId(resultSet.getLong("id"));
                role.setName(resultSet.getString("name"));
                role.setActive(resultSet.getBoolean("active"));
            }
        }
        return role;
    }
}
