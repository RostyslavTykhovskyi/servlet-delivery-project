package com.mycompany.delivery.controller.command.user;

import com.mycompany.delivery.controller.command.Command;
import com.mycompany.delivery.model.entity.User;
import com.mycompany.delivery.model.service.UserService;

import javax.servlet.http.HttpServletRequest;

public class CabinetTopUpCommand implements Command {
    private final UserService userService;

    public CabinetTopUpCommand(UserService userService) {
        this.userService = userService;
    }

    @Override
    public String execute(HttpServletRequest request) {
        int amount = Integer.parseInt(request.getParameter("amount"));
        User user = userService.findByUsername((String) request.getSession().getAttribute("username"));
        user.setBalance(user.getBalance() + amount);
        userService.updateUser(user);
        return "redirect:/cabinet";
    }
}
