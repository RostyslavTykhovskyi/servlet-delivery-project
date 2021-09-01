package com.mycompany.delivery.controller.command.user;

import com.mycompany.delivery.controller.command.Command;
import com.mycompany.delivery.model.entity.User;
import com.mycompany.delivery.model.service.OrderService;
import com.mycompany.delivery.model.service.UserService;

import javax.servlet.http.HttpServletRequest;

public class CabinetCommand implements Command {
    private final UserService userService;
    private final OrderService orderService;

    public CabinetCommand(UserService userService, OrderService orderService) {
        this.userService = userService;
        this.orderService = orderService;
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
            sortField = "order_id";
        }

        String sortDirection = request.getParameter("sortDirection");

        if (sortDirection == null || sortDirection.isEmpty()) {
            sortDirection = "ASC";
        } else {
            sortDirection = sortDirection.equals("ASC") ? "ASC" : "DESC";
        }

        User user = userService.findByUsername((String) request.getSession().getAttribute("username"));

        request.setAttribute("balance", user.getBalance());
        request.setAttribute("orders", orderService.findPaginatedByUserId(user.getId(), page, pageSize, sortField, sortDirection));
        request.setAttribute("pageNumber", orderService.getNumberOfPages(pageSize));
        request.setAttribute("page", page);
        request.setAttribute("sortField", sortField);
        request.setAttribute("sortDirection", sortDirection);
        request.setAttribute("reverseSortDir", sortDirection.equals("ASC") ? "DESC" : "ASC");

        return "/WEB-INF/views/cabinet.jsp";
    }
}
