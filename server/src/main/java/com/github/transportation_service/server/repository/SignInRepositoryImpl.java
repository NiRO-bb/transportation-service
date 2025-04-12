package com.github.transportation_service.server.repository;

import com.github.transportation_service.server.repository.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Repository;

@Repository
public class SignInRepositoryImpl implements SignInRepository {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    // проверить наличие пользователя в базе данных
    public boolean isUserExist(String login) {
        try {
            Integer result = jdbcTemplate.queryForObject("SELECT COUNT(*) FROM USER WHERE LOGIN = ?", Integer.class, login);
            return result > 0;
        } catch (DataAccessException exception) {
            System.out.println(exception.getMessage());
            return false;
        }

    }
    // проверить правильность введенных логина и пароля
    public boolean isUserExist(User user){
        try {
            // хэширование пароля
            BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
            String hashPassword = jdbcTemplate.queryForObject("SELECT PASSWORD FROM USER WHERE LOGIN = ?", String.class, user.getLogin());
            return encoder.matches(user.getPassword(), hashPassword);
        } catch (DataAccessException exception) {
            System.out.println(exception.getMessage());
            return false;
        }
    }
}
