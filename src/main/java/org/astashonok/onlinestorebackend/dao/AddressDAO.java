package org.astashonok.onlinestorebackend.dao;

import org.astashonok.onlinestorebackend.dao.abstracts.EntityDAO;
import org.astashonok.onlinestorebackend.dto.Address;
import org.astashonok.onlinestorebackend.dto.User;

import java.util.List;

public interface AddressDAO extends EntityDAO<Address> {

    //read
    Address getBillingAddressByUser(User user);

    List<Address> getShippingAddressesByUser(User user);

}
