package com.github.transportation_service.server;

import com.github.transportation_service.server.repository.SignInRepository;
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
    private JdbcTemplate jdbcTemplate;

    @Autowired
    SignUpRepository signUpRepository;
    @Autowired
    SignInRepository signInRepository;

    // addUser()
    @Test
    public void shouldAddUserToDB() {
        Assertions.assertFalse(signInRepository.isUserExist("user"));

        int result = signUpRepository.addUser(new User("user", "user"));

        Assertions.assertEquals(1, result);
        Assertions.assertTrue(signInRepository.isUserExist("user"));
    }

    @AfterEach
    public void tearDown() {
        jdbcTemplate.update("DELETE FROM USER");
//        jdbcTemplate.update("INSERT INTO USER VALUES('test', 'test')");
    }
}
