package com.github.transportation_service.server.repository;

import com.github.transportation_service.server.repository.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.jdbc.core.JdbcTemplate;

@Component
public class SignInRepository {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    // проверить наличие пользователя в базе данных
    public boolean isUserExist(String login) {
        Integer result = jdbcTemplate.queryForObject("SELECT COUNT(*) FROM USER WHERE LOGIN = ?", Integer.class, login);
        return result > 0;
    }
    // проверить правильность введенных логина и пароля
    public boolean isUserExist(User user){
        // хэширование пароля
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        String hashPassword;

        try {
             hashPassword = jdbcTemplate.queryForObject("SELECT PASSWORD FROM USER WHERE LOGIN = ?", String.class, user.getLogin());
        } catch (EmptyResultDataAccessException exception) {
            return false;
        }

        return encoder.matches(user.getPassword(), hashPassword);
    }
}