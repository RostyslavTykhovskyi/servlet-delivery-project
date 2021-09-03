package com.mycompany.delivery.controller.command;

import com.mycompany.delivery.model.service.RouteService;
import com.mycompany.delivery.model.validator.Validator;

import javax.servlet.http.HttpServletRequest;

public class MainCommand implements Command {
    private final RouteService routeService;

    public MainCommand(RouteService routeService) {
        this.routeService = routeService;
    }

    @Override
    public String execute(HttpServletRequest request) {
        int pageSize = 5;
        int page = Validator.validatePage(request.getParameter("page"), 1);
        String sortField = Validator.validateSortField(request.getParameter("sortField"), "order_id");
        String sortDirection = Validator.validateSortDirection(request.getParameter("sortDirection"));

        request.setAttribute("routes", routeService.findPaginated(page, pageSize, sortField, sortDirection));
        request.setAttribute("pageNumber", routeService.getNumberOfPages(pageSize));
        request.setAttribute("page", page);
        request.setAttribute("sortField", sortField);
        request.setAttribute("sortDirection", sortDirection);
        request.setAttribute("reverseSortDir", sortDirection.equals("ASC") ? "DESC" : "ASC");

        return "WEB-INF/views/main.jsp";
    }
}
