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
            // open connection
            connection = DriverManager.getConnection(url);

            ps = connection.prepareStatement("INSERT INTO USER VALUES('%s', '%s')".formatted(user.getLogin(), user.getPassword()));
            ps.execute();

            // close connection
            ps.close();
            connection.close();
        }
        catch (SQLException exception) {
            System.out.println(exception.getMessage() + "error caused in SignUpRepository.addUser() method.");
        }
    }

    // найти пользователя по userLogin
    public boolean isUserExist(String login) {
        boolean isExist = false;

        try {
            // open connection
            connection = DriverManager.getConnection(url);
            s = connection.createStatement();
            resultSet = s.executeQuery("SELECT * FROM USER WHERE LOGIN = '%s'".formatted(login));

            if (resultSet.next()) {
                isExist = true;
            }

            // close connection
            resultSet.close();
            s.close();
            connection.close();
        }
        catch (SQLException exception) {
            System.out.println(exception.getMessage() + "error caused in SignUpRepository.isUserExist() method.");
        }

        return isExist;
    }
}
