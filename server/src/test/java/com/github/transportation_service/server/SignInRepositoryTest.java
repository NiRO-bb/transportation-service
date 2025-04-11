package com.github.transportation_service.server;

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
    JdbcTemplate jdbcTemplate;

    @Autowired
    private SignInRepository signInRepository;
    @Autowired
    private SignUpRepository signUpRepository;

    @BeforeEach
    public void setUp() {
        signUpRepository.addUser(new User("test", "test"));
    }

    // isUserExist() - params: String login
    @Test
    public void shouldReturnTrueIfUserExistsByLogin() {
        boolean result = signInRepository.isUserExist("test");
        Assertions.assertTrue(result);

        result = signInRepository.isUserExist("test2");
        Assertions.assertFalse(result);
    }

    // isUserExist() - params: User user
    @Test
    public void shouldReturnTrueIfUserExistsByUserInfo() {
        boolean result = signInRepository.isUserExist(new User("test", "test"));
        Assertions.assertTrue(result);

        result = signInRepository.isUserExist(new User("test2", "test2"));
        Assertions.assertFalse(result);
    }

    @AfterEach
    public void tearDown() {
        jdbcTemplate.update("DELETE FROM USER");
    }
}
