package com.mycompany.delivery.controller.command;

import javax.servlet.http.HttpServletRequest;

public class ExceptionCommand implements Command {
    @Override
    public String execute(HttpServletRequest request) {

        Exception ex = (Exception) request.getAttribute("javax.servlet.error.exception");

        request.setAttribute("errorMessage", ex.getMessage());

        return "WEB-INF/views/error.jsp";
    }
}
