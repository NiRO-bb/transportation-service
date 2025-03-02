package com.github.transportation_service.server.repository;

import com.github.transportation_service.server.repository.entity.User;
import org.springframework.stereotype.Component;

import java.sql.*;

@Component
public class SignInRepository {

    private String url = "jdbc:sqlite:src/main/resources/transportation_service.db";
    private Connection connection;

    private Statement s;
    private PreparedStatement ps;
    private ResultSet resultSet;

    // проверить наличие пользователя в базе данных
    public boolean checkUser(User user) {
        boolean isExist = false;

        try {
            // open connection
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
