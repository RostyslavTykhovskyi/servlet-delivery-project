package com.mycompany.delivery.controller.command;

import com.mycompany.delivery.model.entity.Order;
import com.mycompany.delivery.model.entity.Status;
import com.mycompany.delivery.model.service.OrderService;
import com.mycompany.delivery.model.service.RouteService;
import com.mycompany.delivery.model.service.UserService;
import com.mycompany.delivery.model.validator.Validator;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Map;

public class OrderCommand implements Command {
    private final UserService userService;
    private final RouteService routeService;
    private final OrderService orderService;

    public OrderCommand(UserService userService, RouteService routeService, OrderService orderService) {
        this.userService = userService;
        this.routeService = routeService;
        this.orderService = orderService;
    }

    @Override
    public String execute(HttpServletRequest request) {
        if (request.getMethod().equals("POST") && request.getParameter("calculate") != null) {
            if (!isOrderValid(request)) {
                return "WEB-INF/views/order.jsp";
            }

            calculateOrderCost(request);

            return "WEB-INF/views/order.jsp";

        } else if (request.getMethod().equals("POST") && request.getParameter("makeOrder") != null) {
            if (!isOrderValid(request)) {
                return "WEB-INF/views/order.jsp";
            }

            saveOrder(request);

            return "redirect:/order";
        } else {

        try {
            long route_id = Long.parseLong(request.getParameter("route_id"));
            request.setAttribute("route_id", route_id);
        } catch (NumberFormatException ex) {
        }

        prepareRequest(request);

        return "WEB-INF/views/order.jsp";
        }
    }

    private void prepareRequest(HttpServletRequest request) {
        request.setAttribute("routes", routeService.findAll());
        request.setAttribute("minDimension", Validator.MIN_DIMENSION);
        request.setAttribute("maxDimension", Validator.MAX_DIMENSION);
        request.setAttribute("minWeight", Validator.MIN_WEIGHT);
        request.setAttribute("maxWeight", Validator.MAX_WEIGHT);
        request.setAttribute("minDate", LocalDate.now().plusDays(Validator.MIN_DAYS_AFTER_ORDER));
        request.setAttribute("maxDate", LocalDate.now().plusDays(Validator.MAX_DAYS_AFTER_ORDER));
    }

    private boolean isOrderValid(HttpServletRequest request) {
        String route_id = request.getParameter("route_id");
        String length = request.getParameter("length");
        String width = request.getParameter("width");
        String height = request.getParameter("height");
        String weight = request.getParameter("weight");
        String address = request.getParameter("address");
        String deliveryDate = request.getParameter("deliveryDate");

        Map<String, String> errors;

        if (request.getSession().getAttribute("username") != null) {
            errors = Validator.validateOrderData(length, width, height, weight, address, deliveryDate);
        } else {
            errors = Validator.validateOrderData(length, width, height, weight);
        }

        if (errors.size() > 0) {
            request.setAttribute("errors", errors);
            request.setAttribute("route_id", route_id);
            request.setAttribute("length", length);
            request.setAttribute("width", width);
            request.setAttribute("height", height);
            request.setAttribute("weight", weight);
            request.setAttribute("address", address);
            request.setAttribute("deliveryDate", deliveryDate);

            prepareRequest(request);

            return false;
        }

        return true;
    }

    private void calculateOrderCost(HttpServletRequest request) {
        Order order = new Order();

        order.setRoute(routeService.findById(Long.parseLong(request.getParameter("route_id"))));
        order.setLength(Double.parseDouble(request.getParameter("length")));
        order.setWidth(Double.parseDouble(request.getParameter("width")));
        order.setHeight(Double.parseDouble(request.getParameter("height")));
        order.setWeight(Double.parseDouble(request.getParameter("weight")));

        if (request.getSession().getAttribute("username") != null) {
            order.setAddress(request.getParameter("address"));
            order.setDeliveryDate(LocalDate.parse(request.getParameter("deliveryDate")));
        }

        request.setAttribute("cost", orderService.calculateCost(order));
        request.setAttribute("route_id", order.getRoute().getId());
        request.setAttribute("length", order.getLength());
        request.setAttribute("width", order.getWidth());
        request.setAttribute("height", order.getHeight());
        request.setAttribute("weight", order.getWeight());

        if (request.getSession().getAttribute("username") != null) {
            request.setAttribute("address", order.getAddress());
            request.setAttribute("deliveryDate", order.getDeliveryDate());
        }

        prepareRequest(request);
    }

    private void saveOrder(HttpServletRequest request) {
        Order order = new Order();

        order.setUser(userService.findByUsername((String) request.getSession().getAttribute("username")));
        order.setRoute(routeService.findById(Long.parseLong(request.getParameter("route_id"))));
        order.setLength(Double.parseDouble(request.getParameter("length")));
        order.setWidth(Double.parseDouble(request.getParameter("width")));
        order.setHeight(Double.parseDouble(request.getParameter("height")));
        order.setWeight(Double.parseDouble(request.getParameter("weight")));
        order.setAddress(request.getParameter("address"));
        order.setDeliveryDate(LocalDate.parse(request.getParameter("deliveryDate")));
        order.setCost(orderService.calculateCost(order));
        order.setStatus(Status.PROCESSING);
        order.setCreatedOn(LocalDateTime.now());

        orderService.saveOrder(order);
    }
}
