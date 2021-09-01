package com.mycompany.delivery.model.entity;

public class Route {
    private long id;
    private String departurePoint;
    private String arrivalPoint;
    private int length;

    public Route() {}

    public Route(long id, String departurePoint, String arrivalPoint, int length) {
        this.id = id;
        this.departurePoint = departurePoint;
        this.arrivalPoint = arrivalPoint;
        this.length = length;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getDeparturePoint() {
        return departurePoint;
    }

    public void setDeparturePoint(String departurePoint) {
        this.departurePoint = departurePoint;
    }

    public String getArrivalPoint() {
        return arrivalPoint;
    }

    public void setArrivalPoint(String arrivalPoint) {
        this.arrivalPoint = arrivalPoint;
    }

    public int getLength() {
        return length;
    }

    public void setLength(int length) {
        this.length = length;
    }

    @Override
    public String toString() {
        return "Route{" +
                "id=" + id +
                ", departurePoint='" + departurePoint + '\'' +
                ", arrivalPoint='" + arrivalPoint + '\'' +
                ", length=" + length +
                '}';
    }
}
