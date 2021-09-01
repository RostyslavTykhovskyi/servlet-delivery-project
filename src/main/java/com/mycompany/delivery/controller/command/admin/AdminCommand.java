package com.mycompany.delivery.controller.command.admin;

import com.mycompany.delivery.controller.command.Command;

import javax.servlet.http.HttpServletRequest;

public class AdminCommand implements Command {
    @Override
    public String execute(HttpServletRequest request) {
        return "/WEB-INF/views/admin/admin.jsp";
    }
}
