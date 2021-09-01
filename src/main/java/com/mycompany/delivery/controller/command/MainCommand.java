package com.mycompany.delivery.controller.command;

import com.mycompany.delivery.model.entity.Order;
import com.mycompany.delivery.model.entity.Status;
import com.mycompany.delivery.model.service.OrderService;
import com.mycompany.delivery.model.service.RouteService;
import com.mycompany.delivery.model.service.UserService;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDate;
import java.time.LocalDateTime;

public class MainCommand implements Command {
    private final UserService userService;
    private final RouteService routeService;
    private final OrderService orderService;

    public MainCommand(UserService userService, RouteService routeService, OrderService orderService) {
        this.userService = userService;
        this.routeService = routeService;
        this.orderService = orderService;
    }

    @Override
    public String execute(HttpServletRequest request) {
        if (request.getMethod().equals("POST") && request.getParameter("calculate") != null) {
            Order order = new Order();

            order.setRoute(routeService.findById(Long.parseLong(request.getParameter("route_id"))));
            order.setLength(Integer.parseInt(request.getParameter("length")));
            order.setWidth(Integer.parseInt(request.getParameter("width")));
            order.setHeight(Integer.parseInt(request.getParameter("height")));
            order.setWeight(Integer.parseInt(request.getParameter("weight")));
            if (request.getSession().getAttribute("username") != null) {
                order.setAddress(request.getParameter("address"));
                order.setDeliveryDate(LocalDate.parse(request.getParameter("deliveryDate")));
            }

            request.setAttribute("route_id", order.getRoute().getId());
            request.setAttribute("length", order.getLength());
            request.setAttribute("width", order.getWidth());
            request.setAttribute("height", order.getHeight());
            request.setAttribute("weight", order.getWeight());
            if (request.getSession().getAttribute("username") != null) {
                request.setAttribute("address", order.getAddress());
                request.setAttribute("deliveryDate", order.getDeliveryDate());
            }
            request.setAttribute("cost", orderService.calculateCost(order));
            request.setAttribute("routes", routeService.findAll());
            request.setAttribute("minDate", LocalDate.now().plusDays(3));

            return "WEB-INF/views/main.jsp";
        } else if (request.getMethod().equals("POST") && request.getParameter("makeOrder") != null) {
            Order order = new Order();

            order.setUser(userService.findByUsername((String) request.getSession().getAttribute("username")));
            order.setRoute(routeService.findById(Long.parseLong(request.getParameter("route_id"))));
            order.setLength(Integer.parseInt(request.getParameter("length")));
            order.setWidth(Integer.parseInt(request.getParameter("width")));
            order.setHeight(Integer.parseInt(request.getParameter("height")));
            order.setWeight(Integer.parseInt(request.getParameter("weight")));
            order.setAddress(request.getParameter("address"));
            order.setDeliveryDate(LocalDate.parse(request.getParameter("deliveryDate")));
            order.setCost(orderService.calculateCost(order));
            order.setStatus(Status.PROCESSING);
            order.setCreatedOn(LocalDateTime.now());

            orderService.saveOrder(order);

            return "redirect:/";
        }

        request.setAttribute("routes", routeService.findAll());
        request.setAttribute("minDate", LocalDate.now().plusDays(3));

        return "WEB-INF/views/main.jsp";
    }
}
