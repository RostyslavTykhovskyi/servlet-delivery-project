package com.mycompany.delivery.model.dao.mapper;

import com.mycompany.delivery.model.entity.Route;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Map;

public class RouteMapper implements ObjectMapper<Route> {
    @Override
    public Route extractFromResultSet(ResultSet rs) throws SQLException {
        Route route = new Route();

        route.setId(rs.getInt("route_id"));
        route.setDeparturePoint(rs.getString("departure_point"));
        route.setArrivalPoint(rs.getString("arrival_point"));
        route.setLength(rs.getInt("length"));

        return route;
    }

    @Override
    public Route makeUnique(Map<Long, Route> cache, Route route) {
        cache.putIfAbsent(route.getId(), route);
        return cache.get(route.getId());
    }
}
