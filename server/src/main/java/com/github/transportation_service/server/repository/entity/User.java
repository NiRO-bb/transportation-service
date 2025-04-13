package com.github.transportation_service.server.repository.entity;

import jakarta.validation.constraints.NotBlank;
import org.springframework.validation.annotation.Validated;

@Validated
public class User {

    // логин и пароль пользователя
    @NotBlank(message = "Необходимо заполнить поле \"ЛОГИН\"!")
    private String login;

    @NotBlank(message = "Необходимо заполнить поле \"ПАРОЛЬ\"!")
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