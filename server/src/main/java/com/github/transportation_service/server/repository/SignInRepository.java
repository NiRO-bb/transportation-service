package com.github.transportation_service.server.repository;

import com.github.transportation_service.server.repository.entity.User;
import org.springframework.stereotype.Component;

import java.sql.*;

@Component
public class SignInRepository extends Repository {

    // проверить наличие пользователя в базе данных
    public boolean isUserExist(String login) {
        boolean isExist = false;

        try {
            connection = DriverManager.getConnection(url);
            s = connection.createStatement();
            resultSet = s.executeQuery("SELECT * FROM USER WHERE LOGIN = '%s'".formatted(login));

            if (resultSet.next()) {
                isExist = true;
            }

            resultSet.close();
            s.close();
            connection.close();
        }
        catch (SQLException exception) {
            System.out.println(exception.getMessage() + "error caused in SignUpRepository.isUserExist() method.");
        }

        return isExist;
    }
    public boolean isUserExist(User user) {
        boolean isExist = false;

        try {
            connection = DriverManager.getConnection(url);
            s = connection.createStatement();
            resultSet = s.executeQuery("SELECT * FROM USER WHERE LOGIN = '%s' AND PASSWORD = '%s'".formatted(user.getLogin(), user.getPassword()));

            if (resultSet.next()) {
                isExist = true;
            }

            resultSet.close();
            s.close();
            connection.close();
        }
        catch (SQLException exception) {
            System.out.println(exception.getMessage() + " - error caused in SignInRepository.checkUser() method");
        }

        return isExist;
    }
}
