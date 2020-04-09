package org.astashonok.onlinestore.controller.service;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class ContactPageCommand extends Command {
    private static final String PAGE_JSP = "page.jsp";

    @Override
    public String execute(HttpServletRequest req, HttpServletResponse resp) throws ServletException {
        req.setAttribute("title", "Contact Us");
        req.setAttribute("contactClicked", true);
        return PAGE_JSP;
    }
}
