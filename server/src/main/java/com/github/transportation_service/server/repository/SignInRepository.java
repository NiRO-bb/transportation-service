package com.github.transportation_service.server.repository;

import com.github.transportation_service.server.repository.entity.User;

public interface SignInRepository {

    // проверить наличие пользователя в базе данных
    Result isUserExist(String login);

    // проверить правильность введенных логина и пароля
    Result isUserExist(User user);
}