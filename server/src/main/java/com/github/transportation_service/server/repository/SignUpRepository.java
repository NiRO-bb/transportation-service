package com.github.transportation_service.server.repository;

import com.github.transportation_service.server.repository.entity.User;
import org.springframework.stereotype.Component;

import java.sql.DriverManager;
import java.sql.SQLException;

@Component
public class SignUpRepository extends Repository {

    // добавить пользователя в базу данных
    public void addUser(User user) {
        try {
            connection = DriverManager.getConnection(url);

            ps = connection.prepareStatement("INSERT INTO USER VALUES('%s', '%s')".formatted(user.getLogin(), user.getPassword()));
            ps.execute();

            ps.close();
            connection.close();
        }
        catch (SQLException exception) {
            System.out.println(exception.getMessage() + "error caused in SignUpRepository.addUser() method.");
        }
    }
}
