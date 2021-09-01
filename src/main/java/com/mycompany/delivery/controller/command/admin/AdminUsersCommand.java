package com.mycompany.delivery.controller.command.admin;

import com.mycompany.delivery.controller.command.Command;
import com.mycompany.delivery.model.service.UserService;

import javax.servlet.http.HttpServletRequest;

public class AdminUsersCommand implements Command {
    private final UserService userService;

    public AdminUsersCommand(UserService userService) {
        this.userService = userService;
    }

    @Override
    public String execute(HttpServletRequest request) {
        int pageSize = 5;
        String pageString = request.getParameter("page");

        int page = pageString == null ? 1 : Integer.parseInt(pageString);

        if (page <= 0) {
            page = 1;
        }

        String sortField = request.getParameter("sortField");

        if (sortField == null || sortField.isEmpty()) {
            sortField = "user_id";
        }

        System.out.println(sortField);

        String sortDirection = request.getParameter("sortDirection");

        if (sortDirection == null || sortDirection.isEmpty()) {
            sortDirection = "ASC";
        } else {
            sortDirection = sortDirection.equals("ASC") ? "ASC" : "DESC";
        }

        request.setAttribute("users", userService.findPaginated(page, pageSize, sortField, sortDirection));
        request.setAttribute("pageNumber", userService.getNumberOfPages(pageSize));
        request.setAttribute("page", page);
        request.setAttribute("sortField", sortField);
        request.setAttribute("sortDirection", sortDirection);
        request.setAttribute("reverseSortDir", sortDirection.equals("ASC") ? "DESC" : "ASC");

        return "/WEB-INF/views/admin/users.jsp";
    }
}
