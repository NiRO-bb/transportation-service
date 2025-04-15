package com.github.transportation_service.server;

import com.github.transportation_service.server.repository.Result;
import com.github.transportation_service.server.repository.SignUpRepository;
import com.github.transportation_service.server.repository.entity.User;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.ActiveProfiles;

@SpringBootTest
@ActiveProfiles("test")
public class SignUpRepositoryTest {

    @Autowired
    SignUpRepository r;

    @Test
    public void testAddUser() {
        // корректные параметры
        Result result = r.addUser(new User("login", "password"));
        Assertions.assertTrue((boolean) result.getData());

        // неуникальный логин
        result = r.addUser(new User("login", "password"));
        Assertions.assertFalse((boolean) result.getData());
    }

    @AfterEach
    public void tearDown(@Autowired JdbcTemplate jdbcTemplate) {
        jdbcTemplate.update("DELETE FROM USER");
    }
}
