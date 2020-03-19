package org.astashonok.onlinestorebackend.daoImpl;

import org.astashonok.onlinestorebackend.dto.Description;
import org.astashonok.onlinestorebackend.dto.Product;
import org.astashonok.onlinestorebackend.testconfig.SimpleSingleConnection;
import org.astashonok.onlinestorebackend.util.pool.Pools;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import static org.astashonok.onlinestorebackend.testconfig.StaticInitializerDTO.description1;
import static org.junit.Assert.*;

public class DescriptionDAOImplTest {

    private static DescriptionDAOImpl descriptionDAO;
    private static ProductDAOImpl productDAO;

    @BeforeClass
    public static void init() {
        System.out.println("Initialization of our object! ");
        descriptionDAO = new DescriptionDAOImpl(Pools.newPool(SimpleSingleConnection.class));
        productDAO = new ProductDAOImpl(Pools.newPool(SimpleSingleConnection.class));
    }

    @AfterClass
    public static void destroy() {
        System.out.println("Destruction of our object! ");
        descriptionDAO = null;
        productDAO = null;
    }

    @Test
    public void getByProduct() {
        Description expected = description1;
        Product product = productDAO.getById(1);
        Description actual = descriptionDAO.getByProduct(product);
        assertEquals(expected, actual);
    }

    @Test
    public void getById() {
        Description expected = description1;
        Description actual = descriptionDAO.getById(1);
        assertEquals(expected, actual);
    }

    @Test
    public void edit() {
        Description description = descriptionDAO.getById(1);
        String expected = "HiSilicon";
        description.setProcessor("HiSilicon");
        assertTrue(descriptionDAO.edit(description));
        String actual = descriptionDAO.getById(1).getProcessor();
        assertEquals(expected, actual);

        // database reset
        description.setProcessor("HiSilicon Kirin 980");
        assertTrue(descriptionDAO.edit(description));
    }
}