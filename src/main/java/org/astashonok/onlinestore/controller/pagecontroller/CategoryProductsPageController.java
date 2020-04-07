package org.astashonok.onlinestore.controller.pagecontroller;

import org.astashonok.onlinestore.util.inject.DIServlet;
import org.astashonok.onlinestore.util.inject.Inject;
import org.astashonok.onlinestorebackend.dao.CategoryDAO;
import org.astashonok.onlinestorebackend.dao.ProductDAO;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/show/category/{id}/products")
public class CategoryProductsPageController extends DIServlet {
    private static final String HOME_PATH = "/WEB-INF/views/page.jsp";

    @Inject("categoryDAO")
    private CategoryDAO categoryDAO;

    @Inject("productDAO")
    private ProductDAO productDAO;

    private void processRequest(HttpServletRequest req, HttpServletResponse resp) throws ServletException {

    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException {
        processRequest(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException {
        processRequest(req, resp);
    }
}
