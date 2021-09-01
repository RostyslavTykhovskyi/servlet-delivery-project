package com.mycompany.delivery.controller.command.user;

import com.mycompany.delivery.controller.command.Command;
import com.mycompany.delivery.model.service.OrderService;
import com.mycompany.delivery.model.service.UserService;

import javax.servlet.http.HttpServletRequest;

public class CabinetPayForOrderCommand implements Command {
    private final UserService userService;
    private final OrderService orderService;

    public CabinetPayForOrderCommand(UserService userService, OrderService orderService) {
        this.userService = userService;
        this.orderService = orderService;
    }

    @Override
    public String execute(HttpServletRequest request) {
        userService.payForOrder(orderService.findById(Long.parseLong(request.getParameter("id"))));
        return "redirect:/cabinet";
    }
}
