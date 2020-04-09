package org.astashonok.onlinestore.controller.service;

import org.astashonok.onlinestore.util.inject.DICommand;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public abstract class Command extends DICommand {

    public abstract String execute(HttpServletRequest req, HttpServletResponse resp) throws ServletException;

    String NO_RESULT = null;
    public static Command NO_ACTION = new Command() {
            @Override
            public String execute(HttpServletRequest request, HttpServletResponse response) {
                return NO_RESULT;
            }
        };

    static {
        try {
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
