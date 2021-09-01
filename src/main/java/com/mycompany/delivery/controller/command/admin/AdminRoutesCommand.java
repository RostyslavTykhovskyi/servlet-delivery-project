package com.mycompany.delivery.controller.command.admin;

import com.mycompany.delivery.controller.command.Command;
import com.mycompany.delivery.model.service.RouteService;
import com.mycompany.delivery.model.service.UserService;

import javax.servlet.http.HttpServletRequest;

public class AdminRoutesCommand implements Command {
    private final RouteService routeService;

    public AdminRoutesCommand(RouteService routeService) {
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

        return "/WEB-INF/views/admin/routes.jsp";
    }
}
