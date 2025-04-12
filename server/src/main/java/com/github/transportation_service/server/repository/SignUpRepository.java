package com.github.transportation_service.server.repository;

import com.github.transportation_service.server.repository.entity.User;

public interface SignUpRepository {

    // добавить пользователя в базу данных
    int addUser(User user);
}
