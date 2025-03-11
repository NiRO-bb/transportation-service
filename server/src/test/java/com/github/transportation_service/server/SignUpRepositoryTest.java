package com.github.transportation_service.server;

import com.github.transportation_service.server.repository.SignUpRepository;
import com.github.transportation_service.server.repository.entity.User;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

@SpringBootTest
@ActiveProfiles("test")
public class SignUpRepositoryTest {

    @Autowired
    SignUpRepository signUpRepository;

    @Value("${dbURL}")
    private String url;
    private Connection connection;
    private PreparedStatement ps;

    // addUser
    @Test
    public void shouldAddUserToDB() {
        Assertions.assertFalse(signUpRepository.isUserExist("user"));

        signUpRepository.addUser(new User("user", "user"));

        Assertions.assertTrue(signUpRepository.isUserExist("user"));
    }

    // isUserExist
    @Test
    public void shouldReturnTrueIfUserExists() {
        boolean result = signUpRepository.isUserExist("test");
        Assertions.assertTrue(result);

        result = signUpRepository.isUserExist("test2");
        Assertions.assertFalse(result);
    }

    @AfterEach
    public void tearDown() {
        try {
            // open connection
            connection = DriverManager.getConnection(url);

            ps = connection.prepareStatement("DELETE FROM USER");
            ps.executeUpdate();
            ps = connection.prepareStatement("INSERT INTO USER VALUES('test', 'test')");
            ps.executeUpdate();

            // close connection
            ps.close();
            connection.close();
        }
        catch (SQLException exception) {
            System.out.println(exception.getMessage());
        }
    }
}
