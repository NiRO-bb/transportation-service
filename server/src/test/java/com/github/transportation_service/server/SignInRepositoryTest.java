package com.github.transportation_service.server;

import com.github.transportation_service.server.repository.Result;
import com.github.transportation_service.server.repository.SignInRepository;
import com.github.transportation_service.server.repository.SignUpRepository;
import com.github.transportation_service.server.repository.entity.User;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.ActiveProfiles;

@SpringBootTest
@ActiveProfiles("test")
public class SignInRepositoryTest {

    @Autowired
    private SignInRepository r;

    @BeforeAll
    public static void setUp(@Autowired SignUpRepository signUpRepository) {
        signUpRepository.addUser(new User("login", "password"));
    }

    @Test
    public void testIsUserExistByLogin() {
        // корректные параметры
        Result result = r.isUserExist("login");
        Assertions.assertTrue((boolean) result.getData());

        // некорректный логин
        result = r.isUserExist("invalid login");
        Assertions.assertFalse((boolean) result.getData());
    }

    @Test
    public void testIsUserExistByInfo() {
        // корректные параметры
        Result result = r.isUserExist(new User("login", "password"));
        Assertions.assertTrue((boolean) result.getData());

        // некорректный пароль
        result = r.isUserExist(new User("login", "invalid password"));
        Assertions.assertFalse((boolean) result.getData());

        // некорректный логин
        result = r.isUserExist(new User("invalid login", "password"));
        Assertions.assertFalse((boolean) result.getData());
    }

    @AfterAll
    public static void tearDown(@Autowired JdbcTemplate jdbcTemplate) {
        jdbcTemplate.update("DELETE FROM USER");
    }
}
