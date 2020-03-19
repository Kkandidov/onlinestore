package org.astashonok.onlinestorebackend.daoImpl;

import org.astashonok.onlinestorebackend.dao.AddressDAO;
import org.astashonok.onlinestorebackend.dto.Address;
import org.astashonok.onlinestorebackend.dto.User;
import org.astashonok.onlinestorebackend.exceptions.basicexception.OnlineStoreLogicalException;
import org.astashonok.onlinestorebackend.util.pool.Pool;
import org.astashonok.onlinestorebackend.util.pool.PoolWithDataSource;
import org.astashonok.onlinestorebackend.util.pool.Pools;

import java.sql.*;
import java.util.*;

import static org.astashonok.onlinestorebackend.daoImpl.UserDAOImpl.getUserById;

public class AddressDAOImpl implements AddressDAO {

    private Pool pool;

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
    public Address getBillingAddressByUser(User user) {
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
        } catch (SQLException | OnlineStoreLogicalException e) {
            address = null;
            e.printStackTrace();
        }
        return address;
    }

    @Override
    public List<Address> getShippingAddressesByUser(User user) {
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
        } catch (SQLException | OnlineStoreLogicalException e) {
            addresses = null;
            e.printStackTrace();
        }
        return addresses;
    }

    @Override
    public long add(Address entity) {
        String sql = "INSERT INTO addresses(user_id, line_one, line_two, city, state, country, postal_code, billing"
                + ", shipping) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
        long id = 0;
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
            if (generatedKeys != null) {
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
    public Address getById(long id) {
        String sql = "SELECT id, user_id, line_one, line_two, city, state, country, postal_code, billing, shipping"
                + " FROM addresses WHERE id = " + id;
        Address address = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try (Connection connection = getConnection()) {
            //transaction execution
            connection.setAutoCommit(false);
            try {
                preparedStatement = connection.prepareStatement(sql);
                resultSet = preparedStatement.executeQuery();
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
                    address.setUser(getUserById(userId, connection, preparedStatement, resultSet));
                    connection.commit();
                }
            } catch (SQLException | OnlineStoreLogicalException e) {
                address = null;
                connection.rollback();
                e.printStackTrace();
            } finally {
                if (resultSet != null) {
                    resultSet.close();
                }
                if (preparedStatement != null) {
                    preparedStatement.close();
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return address;
    }

    @Override
    public boolean edit(Address entity) {
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
                throw new SQLException("Creating address is failed, no rows is affected! ");
            }
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean remove(Address entity) {
        String sqlForOrderFetching = entity.isShipping()
                ? "SELECT id FROM orders WHERE shipping_id = " + entity.getId()
                : "SELECT id FROM orders WHERE billing_id = " + entity.getId();
        String sqlForOrderItem = "DELETE FROM order_items WHERE order_id = ";
        String sqlForOrder = "DELETE FROM orders WHERE id = ";
        String sqlForAddress = "DELETE FROM addresses WHERE id = " + entity.getId();
        Statement statement = null;
        ResultSet resultSet;
        List<Long> list = new ArrayList<>();
        try (Connection connection = getConnection()) {
            connection.setAutoCommit(false);
            try {
                statement = connection.createStatement();
                resultSet = statement.executeQuery(sqlForOrderFetching);
                while (resultSet.next()) {
                    list.add(resultSet.getLong("id"));
                }
                for (long i : list) {
                    statement.addBatch(sqlForOrderItem + i);
                    statement.addBatch(sqlForOrder + i);
                }
                statement.addBatch(sqlForAddress);
                if (statement.executeBatch().length == 0) {
                    throw new SQLException("Deleting address is failed! ");
                }
                connection.commit();
                return true;
            } catch (SQLException e) {
                connection.rollback();
                e.printStackTrace();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (statement != null) {
                try {
                    statement.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
        return false;
    }


    public static Address getAddressById(long id, Connection connection, PreparedStatement preparedStatement
            , ResultSet resultSet) throws SQLException, OnlineStoreLogicalException {
        String sql = "SELECT id, user_id, line_one, line_two, city, state, country, postal_code, billing, shipping"
                + " FROM addresses WHERE id = " + id;
        Address address = null;
        preparedStatement = connection.prepareStatement(sql);
        resultSet = preparedStatement.executeQuery();
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
            address.setUser(getUserById(userId, connection, preparedStatement, resultSet));
        }
        return address;
    }
}
