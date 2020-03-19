package org.astashonok.onlinestorebackend.dao.daoImpl;

import org.astashonok.onlinestorebackend.dao.AddressDAO;
import org.astashonok.onlinestorebackend.dto.Address;
import org.astashonok.onlinestorebackend.dto.Role;
import org.astashonok.onlinestorebackend.dto.User;
import org.astashonok.onlinestorebackend.exceptions.basicexception.OnlineStoreLogicalException;
import org.astashonok.onlinestorebackend.util.Pool;
import org.astashonok.onlinestorebackend.util.PoolWithDataSource;
import org.astashonok.onlinestorebackend.util.Pools;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

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
                + "FROM addresses WHERE user_id = ? AND billing = 1";
        Address address;
        ResultSet resultSet = null;
        try (Connection connection = getConnection(); PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setLong(1, user.getId());
            resultSet = preparedStatement.executeQuery();
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
                return address;
            }
        } catch (SQLException | OnlineStoreLogicalException e) {
            e.printStackTrace();
        } finally {
            if(resultSet != null){
                try {
                    resultSet.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
        return null;
    }

    @Override
    public List<Address> getShippingAddressesByUser(User user) {
        String sql = "SELECT id, user_id, line_one, line_two, city, state, country, postal_code, billing, shipping "
                + "FROM addresses WHERE user_id = ? AND shipping = 1";
        List<Address> addresses;
        Address address;
        ResultSet resultSet = null;
        try (Connection connection = getConnection(); PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setLong(1, user.getId());
            addresses = new ArrayList<>();
            resultSet = preparedStatement.executeQuery();
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
            return addresses;
        } catch (SQLException | OnlineStoreLogicalException e) {
            e.printStackTrace();
        }finally {
            if(resultSet != null){
                try {
                    resultSet.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
        return null;
    }

    @Override
    public boolean add(Address entity) {
        String sql = "INSERT INTO addresses(user_id, line_one, line_two, city, state, country, postal_code, billing"
                + ", shipping) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
        try (Connection connection = getConnection(); PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setLong(1, entity.getUser().getId());
            preparedStatement.setString(2, entity.getLineOne());
            preparedStatement.setString(3, entity.getLineTwo());
            preparedStatement.setString(4, entity.getCity());
            preparedStatement.setString(5, entity.getState());
            preparedStatement.setString(6, entity.getCountry());
            preparedStatement.setString(7, entity.getPostalCode());
            preparedStatement.setBoolean(8, entity.isBilling());
            preparedStatement.setBoolean(9, entity.isShipping());
            preparedStatement.executeUpdate();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public Address getById(long id) {
        String sqlForAddress = "SELECT id, user_id, line_one, line_two, city, state, country, postal_code, billing, shipping"
                + " FROM addresses WHERE id = ?";
        String sqlForUser = "SELECT id, first_name, last_name, email, password, contact_number, enabled, role_id FROM users"
                + " WHERE id = ?";
        String sqlForRole = "SELECT id, name, active FROM roles WHERE id = ?";
        Address address;
        User user;
        Role role;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try (Connection connection = getConnection()) {
            //transaction execution
            connection.setAutoCommit(false);
            try {
                preparedStatement = connection.prepareStatement(sqlForAddress);
                preparedStatement.setLong(1, id);
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

                    preparedStatement = connection.prepareStatement(sqlForUser);
                    preparedStatement.setLong(1, userId);
                    resultSet = preparedStatement.executeQuery();
                    if (resultSet.next()) {
                        user = new User();
                        user.setId(resultSet.getLong("id"));
                        user.setFirstName(resultSet.getString("first_name"));
                        user.setLastName(resultSet.getString("last_name"));
                        user.setEmail(resultSet.getString("email"));
                        user.setPassword(resultSet.getString("password"));
                        user.setContactNumber(resultSet.getString("contact_number"));
                        user.setEnabled(resultSet.getBoolean("enabled"));
                        long roleId = resultSet.getLong("role_id");

                        preparedStatement = connection.prepareStatement(sqlForRole);
                        preparedStatement.setLong(1, roleId);
                        resultSet = preparedStatement.executeQuery();
                        if (resultSet.next()) {
                            role = new Role();
                            role.setId(resultSet.getLong("id"));
                            role.setName(resultSet.getString("name"));
                            role.setActive(resultSet.getBoolean("active"));
                            user.setRole(role);
                            address.setUser(user);
                            connection.commit();
                            return address;
                        }
                    }
                }
            } catch (SQLException | OnlineStoreLogicalException e) {
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
        return null;
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
            preparedStatement.executeUpdate();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    //?
    @Override
    public boolean remove(Address entity) {
        return false;
    }
}
