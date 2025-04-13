package com.github.transportation_service.server;

import com.github.transportation_service.server.repository.Result;
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
        Assertions.assertFalse((boolean) signInRepository.isUserExist("user").getData());

        Result result = signUpRepository.addUser(new User("user", "user"));
        Assertions.assertTrue(result.isCorrect());
        Assertions.assertTrue((boolean) result.getData());

        Assertions.assertTrue((boolean) signInRepository.isUserExist("user").getData());
    }

    @AfterEach
    public void tearDown() {
        jdbcTemplate.update("DELETE FROM USER");
    }
}
