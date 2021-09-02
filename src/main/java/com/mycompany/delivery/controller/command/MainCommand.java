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

public class MainCommand implements Command {
    private final RouteService routeService;

    public MainCommand(RouteService routeService) {
        this.routeService = routeService;
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
            sortField = "route_id";
        }

        String sortDirection = request.getParameter("sortDirection");

        if (sortDirection == null || sortDirection.isEmpty()) {
            sortDirection = "ASC";
        } else {
            sortDirection = sortDirection.equals("ASC") ? "ASC" : "DESC";
        }

        request.setAttribute("routes", routeService.findPaginated(page, pageSize, sortField, sortDirection));
        request.setAttribute("pageNumber", routeService.getNumberOfPages(pageSize));
        request.setAttribute("page", page);
        request.setAttribute("sortField", sortField);
        request.setAttribute("sortDirection", sortDirection);
        request.setAttribute("reverseSortDir", sortDirection.equals("ASC") ? "DESC" : "ASC");

        return "WEB-INF/views/main.jsp";
    }
}
