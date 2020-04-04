package org.astashonok.onlinestore.controller.pagecontroller;

import org.astashonok.onlinestore.inject.DIServlet;
import org.astashonok.onlinestore.inject.Inject;
import org.astashonok.onlinestore.util.ServletService;
import org.astashonok.onlinestorebackend.dao.CategoryDAO;
import org.astashonok.onlinestorebackend.exceptions.basicexception.BackendException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(urlPatterns = {"/", "/home", "/index"})
public class StartPage extends DIServlet {
    private static final String HOME_PATH = "/WEB-INF/views/page.jsp";

    @Inject("categoryDAO")
    private CategoryDAO categoryDAO;

    private void processRequest(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            req.setAttribute("title", "Home");
            req.setAttribute("categories", categoryDAO.getAll());
        } catch (BackendException e) {
            e.printStackTrace();
        }
        ServletService.forwardRequest(req, resp, HOME_PATH);
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