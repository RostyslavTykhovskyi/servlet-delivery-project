package com.mycompany.delivery.controller.command.admin;

import com.mycompany.delivery.controller.command.Command;
import com.mycompany.delivery.model.service.UserService;
import com.mycompany.delivery.model.validator.Validator;

import javax.servlet.http.HttpServletRequest;

public class AdminUsersCommand implements Command {
    private final UserService userService;

    public AdminUsersCommand(UserService userService) {
        this.userService = userService;
    }

    @Override
    public String execute(HttpServletRequest request) {
        int pageSize = 5;
        int page = Validator.validatePage(request.getParameter("page"), 1);
        String sortField = Validator.validateSortField(request.getParameter("sortField"), "user_id");
        String sortDirection = Validator.validateSortDirection(request.getParameter("sortDirection"));

        request.setAttribute("users", userService.findPaginated(page, pageSize, sortField, sortDirection));
        request.setAttribute("pageNumber", userService.getNumberOfPages(pageSize));
        request.setAttribute("page", page);
        request.setAttribute("sortField", sortField);
        request.setAttribute("sortDirection", sortDirection);
        request.setAttribute("reverseSortDir", sortDirection.equals("ASC") ? "DESC" : "ASC");

        return "/WEB-INF/views/admin/users.jsp";
    }
}
