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
        boolean result = (boolean) signInRepository.isUserExist("test").getData();
        Assertions.assertTrue(result);

        result = (boolean) signInRepository.isUserExist("test2").getData();
        Assertions.assertFalse(result);
    }

    // isUserExist() - params: User user
    @Test
    public void shouldReturnTrueIfUserExistsByUserInfo() {
        Result result = signInRepository.isUserExist(new User("test", "test"));
        Assertions.assertTrue(result.isCorrect());

        result = signInRepository.isUserExist(new User("test2", "test2"));
        Assertions.assertTrue(result.isCorrect());
        Assertions.assertFalse((boolean) result.getData());
    }

    @AfterEach
    public void tearDown() {
        jdbcTemplate.update("DELETE FROM USER");
    }
}
