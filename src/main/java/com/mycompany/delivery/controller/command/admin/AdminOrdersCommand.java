package com.mycompany.delivery.controller.command.admin;

import com.mycompany.delivery.controller.command.Command;
import com.mycompany.delivery.model.entity.Order;
import com.mycompany.delivery.model.entity.Status;
import com.mycompany.delivery.model.service.OrderService;
import com.mycompany.delivery.model.validator.Validator;

import javax.servlet.http.HttpServletRequest;

public class AdminOrdersCommand implements Command {
    private final OrderService orderService;

    public AdminOrdersCommand(OrderService orderService) {
        this.orderService = orderService;
    }

    @Override
    public String execute(HttpServletRequest request) {
        if (request.getMethod().equals("POST")) {
            Order order = orderService.findById(Long.parseLong(request.getParameter("id")));
            order.setStatus(Status.AWAITING_PAYMENT);
            orderService.updateOrder(order);

            return "redirect:/admin/orders";
        }

        int pageSize = 5;
        int page = Validator.validatePage(request.getParameter("page"), 1);
        String sortField = Validator.validateSortField(request.getParameter("sortField"), "order_id");
        String sortDirection = Validator.validateSortDirection(request.getParameter("sortDirection"));

        request.setAttribute("orders", orderService.findPaginated(page, pageSize, sortField, sortDirection));
        request.setAttribute("pageNumber", orderService.getNumberOfPages(pageSize));
        request.setAttribute("page", page);
        request.setAttribute("sortField", sortField);
        request.setAttribute("sortDirection", sortDirection);
        request.setAttribute("reverseSortDir", sortDirection.equals("ASC") ? "DESC" : "ASC");

        return "/WEB-INF/views/admin/orders.jsp";
    }
}
