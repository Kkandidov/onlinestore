package org.astashonok.onlinestore.controller.service;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class AboutPageCommand extends Command {
    private static final String PAGE_JSP = "page.jsp";

    @Override
    public String execute(HttpServletRequest req, HttpServletResponse resp) throws ServletException {
        req.setAttribute("title", "About Us");
        req.setAttribute("aboutClicked", true);
        return PAGE_JSP;
    }
}
