package com.mycompany.delivery.controller.command;

import javax.servlet.http.HttpServletRequest;

public class LogoutCommand implements Command {
    @Override
    public String execute(HttpServletRequest request) {
        request.getSession(false).removeAttribute("username");
        request.getSession(false).removeAttribute("userRole");
        return "redirect:/";
    }
}
