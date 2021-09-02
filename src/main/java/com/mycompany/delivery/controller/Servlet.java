package com.mycompany.delivery.controller;

import com.mycompany.delivery.controller.command.*;
import com.mycompany.delivery.controller.command.admin.AdminCommand;
import com.mycompany.delivery.controller.command.admin.AdminOrdersCommand;
import com.mycompany.delivery.controller.command.admin.AdminRoutesCommand;
import com.mycompany.delivery.controller.command.admin.AdminUsersCommand;
import com.mycompany.delivery.controller.command.user.CabinetCommand;
import com.mycompany.delivery.controller.command.user.CabinetPayForOrderCommand;
import com.mycompany.delivery.controller.command.user.CabinetTopUpCommand;
import com.mycompany.delivery.model.service.OrderService;
import com.mycompany.delivery.model.service.RouteService;
import com.mycompany.delivery.model.service.UserService;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;


import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class Servlet extends HttpServlet {
    private static final Logger log = LogManager.getLogger(Servlet.class);

    private final UserService userService = new UserService();
    private final RouteService routeService = new RouteService();
    private final OrderService orderService = new OrderService();
    private final Map<String, Command> commands = new HashMap<>();

    public void init(ServletConfig servletConfig){
        commands.put("/", new MainCommand(routeService));
        commands.put("/login", new LoginCommand(userService));
        commands.put("/logout", new LogoutCommand());
        commands.put("/registration", new RegistrationCommand(userService));
        commands.put("/order", new OrderCommand(userService, routeService, orderService));
        commands.put("/cabinet", new CabinetCommand(userService, orderService));
        commands.put("/cabinet/topUp", new CabinetTopUpCommand(userService));
        commands.put("/cabinet/payForOrder", new CabinetPayForOrderCommand(userService, orderService));
        commands.put("/admin", new AdminCommand());
        commands.put("/admin/users", new AdminUsersCommand(userService));
        commands.put("/admin/routes", new AdminRoutesCommand(routeService));
        commands.put("/admin/orders", new AdminOrdersCommand(orderService));
        commands.put("/error" , new ExceptionCommand());
    }

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        processRequest(request, response);
    }

    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        processRequest(request, response);
    }

    private void processRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        log.info("Request uri: " + request.getRequestURI());

        Command command = commands.get(request.getRequestURI());

        if (command == null) {
            log.error("Page not found");
            throw new RuntimeException("Page not found");
        }

        String page = command.execute(request);

        if (page.contains("redirect:")) {
            response.sendRedirect(page.replace("redirect:", ""));
        } else {
            request.getRequestDispatcher(page).forward(request, response);
        }
    }
}
