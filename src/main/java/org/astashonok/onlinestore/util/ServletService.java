package org.astashonok.onlinestore.util;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class ServletService {
    public static void forwardRequest(HttpServletRequest request, HttpServletResponse response
            , String path)
            throws ServletException, IOException {
        request.getRequestDispatcher(path).forward(request, response);
    }

    public static String getRequestParameter(HttpServletRequest request, String name) {
        String param = request.getParameter(name);
        return !param.isEmpty() ? param : "Not provided";
    }
}
