package com.github.transportation_service.server.repository;

import com.github.transportation_service.server.repository.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Repository;

@Repository
public class SignUpRepositoryImpl implements SignUpRepository {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    // добавить пользователя в базу данных
    public int addUser(User user) {
        try {
            // хэширование пароля
            BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

            return jdbcTemplate.update("INSERT INTO USER VALUES(?, ?)", user.getLogin(), encoder.encode(user.getPassword()));
        } catch (DataAccessException exception) {
            System.out.println(exception.getMessage());
            return 0;
        }
    }
}
