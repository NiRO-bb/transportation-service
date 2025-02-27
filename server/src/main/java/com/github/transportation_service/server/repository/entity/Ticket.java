package com.github.transportation_service.server.repository.entity;

public class Ticket {

    // номер билета
    private int id;
    // логин пользователя, забронировавшего билет
    private String userLogin;
    // номер маршрута
    private int route;


    // constructor
    public Ticket(int id, String userLogin, int route) {
        this.id = id;
        this.userLogin = userLogin;
        this.route = route;
    }


    // getters - setters
    public int getRoute() {
        return route;
    }
    public void setRoute(int route) {
        this.route = route;
    }

    public String getUserLogin() {
        return userLogin;
    }
    public void setUserLogin(String userLogin) {
        this.userLogin = userLogin;
    }

    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
}
