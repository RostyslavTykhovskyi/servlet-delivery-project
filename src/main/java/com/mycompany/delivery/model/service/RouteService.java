package com.mycompany.delivery.model.service;

import com.mycompany.delivery.model.dao.DaoFactory;
import com.mycompany.delivery.model.dao.RouteDao;
import com.mycompany.delivery.model.entity.Route;

import java.util.List;

public class RouteService {
    private final DaoFactory daoFactory = DaoFactory.getInstance();

    public List<Route> findPaginated(int page, int pageSize, String sortField, String sortDirection) {
        try (RouteDao routeDao = daoFactory.createRouteDao()) {
            return routeDao.findPaginated(page, pageSize, sortField, sortDirection);
        }
    }

    public int getNumberOfPages(int pageSize) {
        try (RouteDao routeDao = daoFactory.createRouteDao()) {
            return (int) Math.ceil((double) routeDao.getNumberOfRows() / pageSize);
        }
    }

    public void saveRoute(Route route) {
        try (RouteDao routeDao = daoFactory.createRouteDao()) {
            routeDao.save(route);
        }
    }

    public Route findById(long id) {
        try (RouteDao routeDao = daoFactory.createRouteDao()) {
            return routeDao.findById(id).orElseThrow(() -> new RuntimeException("Route with id " + id + " was not found"));
        }
    }

    public List<Route> findAll() {
        try (RouteDao routeDao = daoFactory.createRouteDao()) {
            return routeDao.findAll();
        }
    }

    public void deleteRoute(long id) {
        try (RouteDao routeDao = daoFactory.createRouteDao()) {
            routeDao.delete(id);
        }
    }
}
