package com.github.transportation_service.server.repository;

import com.github.transportation_service.server.repository.entity.User;

public interface SignInRepository {

    // проверить наличие пользователя в базе данных
    boolean isUserExist(String login);

    // проверить правильность введенных логина и пароля
    boolean isUserExist(User user);
}