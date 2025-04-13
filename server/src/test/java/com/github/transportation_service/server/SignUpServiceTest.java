package com.github.transportation_service.server;

import com.github.transportation_service.server.repository.Result;
import com.github.transportation_service.server.repository.entity.User;
import com.github.transportation_service.server.service.SignUpService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.ActiveProfiles;

@SpringBootTest
@ActiveProfiles("test")
public class SignUpServiceTest {

    @Autowired
    JdbcTemplate jdbcTemplate;
    @Autowired
    SignUpService signUpService;

    // createAccount()
    @Test
    public void shouldCreateUser() {
        Result result = signUpService.createAccount(new User("user", "user"));
        // создан новый аккаунт
        Assertions.assertTrue((boolean) result.getData());

        result = signUpService.createAccount(new User("user", "user"));
        // найден аккаунт с таким же логином
        Assertions.assertFalse((boolean) result.getData());
    }

    @AfterEach
    public void tearDown() {
        jdbcTemplate.update("DELETE FROM USER");
    }
}
