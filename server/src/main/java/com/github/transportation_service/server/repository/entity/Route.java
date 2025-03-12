package com.github.transportation_service.server.repository.entity;

import java.time.LocalDate;
import java.time.LocalTime;

public class Route {

    // номер маршрута
    private int id;
    // тип транспорта
    private String transport;
    // количество свободных мест
    private int places;

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


    // constructor
    public Route() {}
    public Route(int id, String transport, int places, String departurePoint, String arrivalPoint, LocalDate departureDate, LocalTime departureTime, LocalDate arrivalDate, LocalTime arrivalTime) {
        this.id = id;
        this.transport = transport;
        this.places = places;
        this.departurePoint = departurePoint;
        this.arrivalPoint = arrivalPoint;
        this.departureDate = departureDate;
        this.departureTime = departureTime;
        this.arrivalDate = arrivalDate;
        this.arrivalTime = arrivalTime;
    }


    // getters - setters
    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }

    public String getTransport() {
        return transport;
    }
    public void setTransport(String transport) {
        this.transport = transport;
    }

    public int getPlaces() {
        return places;
    }
    public void setPlaces(int places) {
        this.places = places;
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
