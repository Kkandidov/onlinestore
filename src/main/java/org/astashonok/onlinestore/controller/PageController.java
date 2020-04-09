package org.astashonok.onlinestore.controller;

import org.astashonok.onlinestore.controller.service.Command;
import org.astashonok.onlinestore.util.CommandContainer;
import org.astashonok.onlinestore.util.inject.DIServlet;
import org.astashonok.onlinestore.util.ServletService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/")
public class PageController extends DIServlet {
    private static final String PATH = "/WEB-INF/views/";
    private CommandContainer commandContainer = CommandContainer.getInstance();

    private void processRequest(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Command command = commandContainer.getCommand(req.getParameter("command"));
        ServletService.forwardRequest(req, resp, PATH + command.execute(req, resp));
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
