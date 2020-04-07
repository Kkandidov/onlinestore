package org.astashonok.onlinestore.controller.pagecontroller;

import org.astashonok.onlinestore.util.inject.DIServlet;
import org.astashonok.onlinestore.util.inject.Inject;
import org.astashonok.onlinestore.util.ServletService;
import org.astashonok.onlinestorebackend.dao.CategoryDAO;
import org.astashonok.onlinestorebackend.exceptions.basicexception.BackendException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/show/all/products")
public class AllProductsPageController extends DIServlet {
    private static final String HOME_PATH = "/WEB-INF/views/page.jsp";

    @Inject("categoryDAO")
    private CategoryDAO categoryDAO;

    private void processRequest(HttpServletRequest req, HttpServletResponse resp) throws ServletException {
        try {
            req.setAttribute("title", "All Products");
            req.setAttribute("categories", categoryDAO.getAll());
            req.setAttribute("allProductsClicked", true);
        } catch (BackendException e) {
            e.printStackTrace();
        }
        try {
            ServletService.forwardRequest(req, resp, HOME_PATH);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        processRequest(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        processRequest(req, resp);
    }
}
