package org.astashonok.onlinestore.controller.service;

import org.astashonok.onlinestore.util.inject.Inject;
import org.astashonok.onlinestorebackend.dao.DescriptionDAO;
import org.astashonok.onlinestorebackend.dao.ProductDAO;
import org.astashonok.onlinestorebackend.dao.ViewDAO;
import org.astashonok.onlinestorebackend.dto.Product;
import org.astashonok.onlinestorebackend.exceptions.basicexception.BackendException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class SingleProductPageCommand extends Command {
    private static final String PAGE_JSP = "page.jsp";

    @Inject("productDAO")
    private ProductDAO productDAO;
    @Inject("descriptionDAO")
    private DescriptionDAO descriptionDAO;
    @Inject("viewDAO")
    private ViewDAO viewDAO;

    @Override
    public String execute(HttpServletRequest req, HttpServletResponse resp) throws ServletException {
        try {
            Product product = productDAO.getById(Long.parseLong(req.getParameter("id")));
            req.setAttribute("title", product.getName());
            req.setAttribute("product", product);
            req.setAttribute("description", descriptionDAO.getByProduct(product));
            req.setAttribute("views", viewDAO.getByProduct(product));
            req.setAttribute("showSingleProductClicked", true);
        } catch (BackendException e) {
            e.printStackTrace();
        }
        return PAGE_JSP;
    }
}
