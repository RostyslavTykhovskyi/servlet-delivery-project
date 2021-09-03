package com.mycompany.delivery.controller.command.admin;

import com.mycompany.delivery.controller.command.Command;
import com.mycompany.delivery.model.service.RouteService;
import com.mycompany.delivery.model.service.UserService;
import com.mycompany.delivery.model.validator.Validator;

import javax.servlet.http.HttpServletRequest;

public class AdminRoutesCommand implements Command {
    private final RouteService routeService;

    public AdminRoutesCommand(RouteService routeService) {
        this.routeService = routeService;
    }

    @Override
    public String execute(HttpServletRequest request) {
        int pageSize = 5;
        int page = Validator.validatePage(request.getParameter("page"), 1);
        String sortField = Validator.validateSortField(request.getParameter("sortField"), "route_id");
        String sortDirection = Validator.validateSortDirection(request.getParameter("sortDirection"));

        request.setAttribute("routes", routeService.findPaginated(page, pageSize, sortField, sortDirection));
        request.setAttribute("pageNumber", routeService.getNumberOfPages(pageSize));
        request.setAttribute("page", page);
        request.setAttribute("sortField", sortField);
        request.setAttribute("sortDirection", sortDirection);
        request.setAttribute("reverseSortDir", sortDirection.equals("ASC") ? "DESC" : "ASC");

        return "/WEB-INF/views/admin/routes.jsp";
    }
}
