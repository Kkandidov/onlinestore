package org.astashonok.onlinestore.controller.service;

import org.astashonok.onlinestore.util.inject.Inject;
import org.astashonok.onlinestorebackend.dao.CategoryDAO;
import org.astashonok.onlinestorebackend.exceptions.basicexception.BackendException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class AllProductsPageCommand extends Command {
    private static final String PAGE_JSP = "page.jsp";

    @Inject("categoryDAO")
    private CategoryDAO categoryDAO;

    @Override
    public String execute(HttpServletRequest req, HttpServletResponse resp) throws ServletException {
        try {
            req.setAttribute("title", "All Products");
            req.setAttribute("categories", categoryDAO.getAll());
            req.setAttribute("allProductsClicked", true);
        } catch (BackendException e) {
            e.printStackTrace();
        }
        return PAGE_JSP;
    }
}
