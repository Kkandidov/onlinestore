package org.astashonok.onlinestore.controller.pagecontroller;

import org.astashonok.onlinestore.util.inject.DIServlet;
import org.astashonok.onlinestore.util.ServletService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/contact")
public class ContactPageController extends DIServlet {
    private static final String HOME_PATH = "/WEB-INF/views/page.jsp";

    private void processRequest(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            req.setAttribute("title", "Contact Us");
            req.setAttribute("contactClicked", true);
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
