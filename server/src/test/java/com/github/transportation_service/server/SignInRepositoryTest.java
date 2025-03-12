package com.github.transportation_service.server;

import com.github.transportation_service.server.repository.SignInRepository;
import com.github.transportation_service.server.repository.entity.User;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

@SpringBootTest
@ActiveProfiles("test")
public class SignInRepositoryTest {

    @Autowired
    private SignInRepository signInRepository;

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
}
