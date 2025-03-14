package com.github.transportation_service.server;

import com.github.transportation_service.server.repository.SignInRepository;
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
    @Autowired
    SignInRepository signInRepository;

    @Value("${spring.datasource.url}")
    private String url;
    private Connection connection;
    private PreparedStatement ps;

    // addUser()
    @Test
    public void shouldAddUserToDB() {
        Assertions.assertFalse(signInRepository.isUserExist("user"));

        signUpRepository.addUser(new User("user", "user"));

        Assertions.assertTrue(signInRepository.isUserExist("user"));
    }

    @AfterEach
    public void tearDown() {
        try {
            connection = DriverManager.getConnection(url);

            ps = connection.prepareStatement("DELETE FROM USER");
            ps.executeUpdate();
            ps = connection.prepareStatement("INSERT INTO USER VALUES('test', 'test')");
            ps.executeUpdate();

            ps.close();
            connection.close();
        }
        catch (SQLException exception) {
            System.out.println(exception.getMessage());
        }
    }
}
