package com.github.transportation_service.server.repository.entity;

import java.time.LocalDate;
import java.time.LocalTime;

public class Route {

    // номер маршрута
    private int id;
    // тип транспорта
    private Transport transport;
    // количество свободных мест
    private int Places;

    // пункт отправления
    private String departurePoint;
    // пункт прибытия
    private String arrivalPoint;

    // дата и время отправления
    private LocalDate departureDate;
    private LocalTime departureTime;
    // дата и время прибытия
    private LocalDate arrivalDate;
    private LocalTime arrivalTime;

    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }

    public Transport getTransport() {
        return transport;
    }
    public void setTransport(Transport transport) {
        this.transport = transport;
    }

    public int getPlaces() {
        return Places;
    }
    public void setPlaces(int places) {
        Places = places;
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

    public LocalDate getDepartureDate() {
        return departureDate;
    }
    public void setDepartureDate(LocalDate departureDate) {
        this.departureDate = departureDate;
    }

    public LocalTime getDepartureTime() {
        return departureTime;
    }
    public void setDepartureTime(LocalTime departureTime) {
        this.departureTime = departureTime;
    }

    public LocalDate getArrivalDate() {
        return arrivalDate;
    }
    public void setArrivalDate(LocalDate arrivalDate) {
        this.arrivalDate = arrivalDate;
    }

    public LocalTime getArrivalTime() {
        return arrivalTime;
    }
    public void setArrivalTime(LocalTime arrivalTime) {
        this.arrivalTime = arrivalTime;
    }
}
