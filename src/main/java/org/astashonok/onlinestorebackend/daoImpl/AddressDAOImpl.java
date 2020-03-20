package org.astashonok.onlinestorebackend.daoImpl;

import org.astashonok.onlinestorebackend.dao.AddressDAO;
import org.astashonok.onlinestorebackend.dto.Address;
import org.astashonok.onlinestorebackend.dto.User;
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
import java.util.*;

import static org.astashonok.onlinestorebackend.daoImpl.UserDAOImpl.getUserById;

public class AddressDAOImpl implements AddressDAO {

    private Pool pool;
    private static final Logger logger = LoggerFactory.getLogger(ClassName.getCurrentClassName());

    public AddressDAOImpl(Pool pool) {
        this.pool = pool;
    }

    public AddressDAOImpl() {
        pool = Pools.newPool(PoolWithDataSource.class);
    }

    public void setPool(Pool pool) {
        this.pool = pool;
    }

    private Connection getConnection() throws SQLException {
        return pool.getConnection();
    }

    @Override
    public Address getBillingAddressByUser(User user) throws BackendException {
        logger.info("Invoking of getBillingAddressByUser(User user) method: {}", user);
        String sql = "SELECT id, user_id, line_one, line_two, city, state, country, postal_code, billing, shipping "
                + "FROM addresses WHERE billing = 1 AND user_id = " + user.getId();
        Address address = null;
        try (Connection connection = getConnection(); PreparedStatement preparedStatement = connection.prepareStatement(sql)
             ; ResultSet resultSet = preparedStatement.executeQuery()) {
            if (resultSet.next()) {
                address = new Address();
                address.setId(resultSet.getLong("id"));
                address.setUser(user);
                address.setLineOne(resultSet.getString("line_one"));
                address.setLineTwo(resultSet.getString("line_two"));
                address.setCity(resultSet.getString("city"));
                address.setState(resultSet.getString("state"));
                address.setCountry(resultSet.getString("country"));
                address.setPostalCode(resultSet.getString("postal_code"));
                address.setBilling(resultSet.getBoolean("billing"));
                address.setShipping(resultSet.getBoolean("shipping"));
            }
            logger.info("The address was fetched from database: {}", address);
        } catch (BackendLogicalException e) {
            logger.error("No address was fetched from database", e);
            throw e;
        } catch (SQLException e) {
            logger.error("SQLException", e);
            throw new DatabaseException("SQLException", e);
        }
        return address;
    }

    @Override
    public List<Address> getShippingAddressesByUser(User user) throws BackendException {
        logger.info("Invoking of getShippingAddressesByUser(User user) method: {}", user);
        String sql = "SELECT id, user_id, line_one, line_two, city, state, country, postal_code, billing, shipping "
                + "FROM addresses WHERE shipping = 1 ANd user_id = " + user.getId();
        List<Address> addresses = new ArrayList<>();
        Address address;
        try (Connection connection = getConnection(); PreparedStatement preparedStatement = connection.prepareStatement(sql)
             ; ResultSet resultSet = preparedStatement.executeQuery()) {
            while (resultSet.next()) {
                address = new Address();
                address.setId(resultSet.getLong("id"));
                address.setUser(user);
                address.setLineOne(resultSet.getString("line_one"));
                address.setLineTwo(resultSet.getString("line_two"));
                address.setCity(resultSet.getString("city"));
                address.setState(resultSet.getString("state"));
                address.setCountry(resultSet.getString("country"));
                address.setPostalCode(resultSet.getString("postal_code"));
                address.setBilling(resultSet.getBoolean("billing"));
                address.setShipping(resultSet.getBoolean("shipping"));
                addresses.add(address);
            }
            logger.info("The addresses were fetched from database: {}", addresses);
        } catch (BackendLogicalException e) {
            logger.error("No addresses were fetched from database", e);
            throw e;
        } catch (SQLException e) {
            logger.error("SQLException", e);
            throw new DatabaseException("SQLException", e);
        }
        return addresses;
    }

    @Override
    public long add(Address entity) throws BackendException {
        logger.info("Invoking of add(Address entity) method: {}", entity);
        String sql = "INSERT INTO addresses(user_id, line_one, line_two, city, state, country, postal_code, billing"
                + ", shipping) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
        long id;
        ResultSet generatedKeys = null;
        try (Connection connection = getConnection()
             ; PreparedStatement preparedStatement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            preparedStatement.setLong(1, entity.getUser().getId());
            preparedStatement.setString(2, entity.getLineOne());
            preparedStatement.setString(3, entity.getLineTwo());
            preparedStatement.setString(4, entity.getCity());
            preparedStatement.setString(5, entity.getState());
            preparedStatement.setString(6, entity.getCountry());
            preparedStatement.setString(7, entity.getPostalCode());
            preparedStatement.setBoolean(8, entity.isBilling());
            preparedStatement.setBoolean(9, entity.isShipping());
            if (preparedStatement.executeUpdate() == 0) {
                logger.error("Creating the address is failed, no rows is affected");
                throw new DatabaseException("Creating the address is failed, no rows is affected");
            }
            generatedKeys = preparedStatement.getGeneratedKeys();
            if (generatedKeys.next()) {
                id = generatedKeys.getLong(1);
                entity.setId(id);
            } else {
                logger.error("The address key isn't fetched from database");
                throw new DatabaseException("The address key isn't fetched from database");
            }
            logger.info("The address was added, its id = {}", id);
        } catch (BackendLogicalException e) {
            logger.error("The address wasn't added", e);
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
    public Address getById(long id) throws BackendException {
        logger.info("Invoking of getById(long id), id = {}", id);
        Address address;
        try (Connection connection = getConnection()) {
            connection.setAutoCommit(false);
            try {
                address = getAddressById(id, connection);
                connection.commit();
                logger.info("The address was fetched from database: {}", address);
            } catch (BackendLogicalException e) {
                connection.rollback();
                logger.error("The address wasn't fetched from database", e);
                throw e;
            } catch (SQLException e) {
                connection.rollback();
                logger.error("SQLException", e);
                throw new DatabaseException("SQLException", e);
            }
        } catch (SQLException e) {
            logger.error("SQLException", e);
            throw new DatabaseException("SQLException", e);
        }
        return address;
    }

    @Override
    public boolean edit(Address entity) throws BackendException {
        logger.info("Invoking of edit(Address entity): {} ", entity);
        String sql = "UPDATE addresses SET user_id = ?, line_one = ?, line_two = ?, city = ?, state = ?, country = ?"
                + ", postal_code = ?, billing = ?, shipping = ? WHERE id = ?";
        try (Connection connection = getConnection(); PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setLong(10, entity.getId());
            preparedStatement.setLong(1, entity.getUser().getId());
            preparedStatement.setString(2, entity.getLineOne());
            preparedStatement.setString(3, entity.getLineTwo());
            preparedStatement.setString(4, entity.getCity());
            preparedStatement.setString(5, entity.getState());
            preparedStatement.setString(6, entity.getCountry());
            preparedStatement.setString(7, entity.getPostalCode());
            preparedStatement.setBoolean(8, entity.isBilling());
            preparedStatement.setBoolean(9, entity.isShipping());
            if (preparedStatement.executeUpdate() == 0) {
                logger.error("Updating the address is failed, no rows is affected");
                throw new DatabaseException("Updating the address is failed, no rows is affected");
            }
            logger.info("The address was updated");
            return true;
        } catch (SQLException e) {
            logger.error("SQLException", e);
            throw new DatabaseException("SQLException", e);
        }
    }

    // ENGINE = InnoDB and ON DELETE CASCADE are used in this project
    @Override
    public boolean remove(Address entity) throws BackendException {
        logger.info("Invoking of remove(Address entity): {}", entity);
        String sql = "DELETE FROM addresses WHERE id = " + entity.getId();
        try (Connection connection = getConnection(); PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            if (preparedStatement.executeUpdate() == 0) {
                logger.error("Deleting the address is failed, no rows is affected");
                throw new DatabaseException("Deleting the address is failed, no rows is affected");
            }
            logger.info("The address was deleted successfully");
            return true;
        } catch (SQLException e) {
            logger.error("SQLException", e);
            throw new DatabaseException("SQLException", e);
        }
    }


    static Address getAddressById(long id, Connection connection) throws SQLException, BackendLogicalException {
        String sql = "SELECT id, user_id, line_one, line_two, city, state, country, postal_code, billing, shipping"
                + " FROM addresses WHERE id = " + id;
        Address address = null;
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)
             ; ResultSet resultSet = preparedStatement.executeQuery()) {
            if (resultSet.next()) {
                address = new Address();
                address.setId(resultSet.getLong("id"));
                address.setLineOne(resultSet.getString("line_one"));
                address.setLineTwo(resultSet.getString("line_two"));
                address.setCity(resultSet.getString("city"));
                address.setState(resultSet.getString("state"));
                address.setCountry(resultSet.getString("country"));
                address.setPostalCode(resultSet.getString("postal_code"));
                address.setBilling(resultSet.getBoolean("billing"));
                address.setShipping(resultSet.getBoolean("shipping"));
                long userId = resultSet.getLong("user_id");
                address.setUser(getUserById(userId, connection));
            }
        }
        return address;
    }
}
