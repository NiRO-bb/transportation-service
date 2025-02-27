package com.github.transportation_service.server.repository.entity;

public class User {

    // логин и пароль пользователя
    private String login;
    private String password;


    // constructor
    public User(String login, String password) {
        this.login = login;
        this.password = password;
    }


    // getters - setters
    public String getLogin() {
        return login;
    }
    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }
    public void setPassword(String password) {
        this.password = password;
    }
}