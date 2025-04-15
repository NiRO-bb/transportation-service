package com.github.transportation_service.server;

import com.github.transportation_service.server.controller.SignUpController;
import com.github.transportation_service.server.repository.SignUpRepository;
import com.github.transportation_service.server.repository.entity.User;
import jakarta.validation.ConstraintViolationException;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.ActiveProfiles;

@SpringBootTest
@ActiveProfiles("test")
public class SignUpControllerTest {

    @Autowired
    SignUpController c;

    @BeforeAll
    public static void setUp(@Autowired SignUpRepository signUpRepository) {
        signUpRepository.addUser(new User("login", "password"));
    }

    @Test
    public void testCreateAccount() {
        // корректные параметры
        ResponseEntity<?> result = c.createAccount(new User("new login", "password"));
        Assertions.assertEquals(HttpStatus.OK, result.getStatusCode());

        // некорректные параметры
        result = c.createAccount(new User("login", "password"));
        Assertions.assertEquals(HttpStatus.BAD_REQUEST, result.getStatusCode());

        Assertions.assertThrows(ConstraintViolationException.class, () -> {
            c.createAccount(new User("login", ""));
        });

        Assertions.assertThrows(ConstraintViolationException.class, () -> {
            c.createAccount(new User("", "password"));
        });
    }

    @AfterAll
    public static void tearDown(@Autowired JdbcTemplate jdbcTemplate) {
        jdbcTemplate.update("DELETE FROM USER");
    }
}
