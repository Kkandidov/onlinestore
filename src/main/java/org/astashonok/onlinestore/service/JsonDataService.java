package org.astashonok.onlinestore.service;
import org.astashonok.onlinestorebackend.dao.CategoryDAO;
import org.astashonok.onlinestorebackend.dao.ProductDAO;
import org.astashonok.onlinestorebackend.daoImpl.CategoryDAOImpl;
import org.astashonok.onlinestorebackend.daoImpl.ProductDAOImpl;
import org.astashonok.onlinestorebackend.dto.Product;
import org.astashonok.onlinestorebackend.exceptions.basicexception.BackendException;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.util.List;

@Path("/json/data")
public class JsonDataService {

    private ProductDAO productDAO = new ProductDAOImpl();
    private CategoryDAO categoryDAO = new CategoryDAOImpl();

    @GET
    @Path("/all/products")
    @Produces(MediaType.APPLICATION_JSON)
    public List<Product> getAllActiveProducts(){
        try {
            return productDAO.getAllActive();
        } catch (BackendException e) {
            e.printStackTrace();
        }
        return null;
    }

    @GET
    @Path("/category/{id}/products")
    @Produces(MediaType.APPLICATION_JSON)
    public List<Product> getAllActiveProductsByCategory(@PathParam("id") long id){
        try {
            return productDAO.getAllActiveByCategory(categoryDAO.getById(id));
        } catch (BackendException e) {
            e.printStackTrace();
        }
        return null;
    }
}
