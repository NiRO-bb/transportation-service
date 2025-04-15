package com.github.transportation_service.server.repository;

import com.github.transportation_service.server.repository.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class SignInRepositoryImpl implements SignInRepository {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    // проверить наличие пользователя в базе данных
    public Result isUserExist(String login) {
        try {
            int result = jdbcTemplate.queryForObject("SELECT COUNT(*) FROM USER WHERE LOGIN = ?", Integer.class, login);
            return new Result(result > 0, true);
        } catch (DataAccessException exception) {
            return new Result(null, false);
        }

    }
    // проверить правильность введенных логина и пароля
    public Result isUserExist(User user){
        try {
            // хэширование пароля
            BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
            List<String> hashPassword = jdbcTemplate.query("SELECT PASSWORD FROM USER WHERE LOGIN = ?", (rs, rowNum) -> rs.getString("PASSWORD"), user.getLogin());
            if (!hashPassword.isEmpty())
                return new Result(encoder.matches(user.getPassword(), hashPassword.get(0)), true);
            return new Result(false, true);
        } catch (DataAccessException exception) {
            return new Result(null, false);
        }
    }
}
