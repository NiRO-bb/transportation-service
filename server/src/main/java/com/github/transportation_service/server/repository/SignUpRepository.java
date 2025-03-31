package com.github.transportation_service.server.repository;

import com.github.transportation_service.server.repository.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

@Component
public class SignUpRepository {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    // добавить пользователя в базу данных
    public void addUser(User user) {
        jdbcTemplate.update("INSERT INTO USER VALUES(?, ?)", user.getLogin(), user.getPassword());
    }
}
