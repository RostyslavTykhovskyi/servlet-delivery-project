package com.mycompany.delivery.model.service;

import com.mycompany.delivery.model.dao.DaoFactory;
import com.mycompany.delivery.model.dao.RouteDao;
import com.mycompany.delivery.model.entity.Route;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import java.util.List;

public class RouteService {
    private static final Logger log = LogManager.getLogger(RouteService.class);

    private final DaoFactory daoFactory = DaoFactory.getInstance();

    public List<Route> findPaginated(int page, int pageSize, String sortField, String sortDirection) {
        log.info("Finding paginated routes");
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
        log.info("Saving route");
        try (RouteDao routeDao = daoFactory.createRouteDao()) {
            routeDao.save(route);
        }
    }

    public Route findById(long id) {
        log.info("Finding route with id = " + id);
        try (RouteDao routeDao = daoFactory.createRouteDao()) {
            return routeDao.findById(id).orElseThrow(() -> new RuntimeException("Route with id " + id + " was not found"));
        }
    }

    public List<Route> findAll() {
        log.info("Finding all routes");
        try (RouteDao routeDao = daoFactory.createRouteDao()) {
            return routeDao.findAll();
        }
    }
}
