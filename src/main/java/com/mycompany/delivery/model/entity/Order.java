package com.mycompany.delivery.model.entity;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class Order {
    private long id;
    private int cost;
    private double weight;
    private double length;
    private double width;
    private double height;
    private String address;
    private LocalDate deliveryDate;
    private LocalDateTime createdOn;

    private Status status;
    private User user;
    private Route route;

    public Order() {}

    public Order(long id, int cost, double weight, double length, double width, double height,
                 String address, LocalDate deliveryDate, Status status,
                 LocalDateTime createdOn, User user, Route route) {
        this.id = id;
        this.cost = cost;
        this.weight = weight;
        this.length = length;
        this.width = width;
        this.height = height;
        this.address = address;
        this.deliveryDate = deliveryDate;
        this.status = status;
        this.createdOn = createdOn;
        this.user = user;
        this.route = route;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public int getCost() {
        return cost;
    }

    public void setCost(int cost) {
        this.cost = cost;
    }

    public double getWeight() {
        return weight;
    }

    public void setWeight(double weight) {
        this.weight = weight;
    }

    public double getLength() {
        return length;
    }

    public void setLength(double length) {
        this.length = length;
    }

    public double getWidth() {
        return width;
    }

    public void setWidth(double width) {
        this.width = width;
    }

    public double getHeight() {
        return height;
    }

    public void setHeight(double height) {
        this.height = height;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public LocalDate getDeliveryDate() {
        return deliveryDate;
    }

    public void setDeliveryDate(LocalDate deliveryDate) {
        this.deliveryDate = deliveryDate;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public LocalDateTime getCreatedOn() {
        return createdOn;
    }

    public void setCreatedOn(LocalDateTime createdOn) {
        this.createdOn = createdOn;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Route getRoute() {
        return route;
    }

    public void setRoute(Route route) {
        this.route = route;
    }

    @Override
    public String toString() {
        return "Order{" +
                "id=" + id +
                ", cost=" + cost +
                ", weight=" + weight +
                ", length=" + length +
                ", width=" + width +
                ", height=" + height +
                ", address='" + address + '\'' +
                ", deliveryDate=" + deliveryDate +
                ", createdOn=" + createdOn +
                ", status=" + status +
                ", user=" + user +
                ", route=" + route +
                '}';
    }
}
